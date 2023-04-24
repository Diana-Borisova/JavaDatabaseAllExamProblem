package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Paths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtils validationUtils;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count()>0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRIES_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readCountriesFromFile(), CountryImportDto[].class))
                .filter(countryImportDto -> {
                    boolean isValid = validationUtils.isValid(countryImportDto);
                    Optional<Country> country = this.countryRepository.findCountryByCountryName(countryImportDto.getCountryName());

                    if (country.isPresent()){
                        isValid = false;
                    }
                    return isValid;
                })
                .map(countryImportDto -> {
                    Country country = modelMapper.map(countryImportDto, Country.class);
                    return country;
                })
                .forEach(this.countryRepository::save);
        return null;
    }
}
