package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Messages.*;
import static softuni.exam.constants.Paths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count()>0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(COUNTRIES_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb= new StringBuilder();
        Arrays.stream(gson.fromJson(readCountriesFileContent(), CountryImportDto[].class ))
                .filter(countryImportDto -> {
                    boolean isValid = validationUtil.isValid(countryImportDto);
                    Optional<Country> country = this.countryRepository.findByNameAndCode(countryImportDto.getName(), countryImportDto.getCountryCode());

                    if (country.isPresent() ){
                        isValid = false;
                    }
                    sb.append(isValid ? String.format(VALID_COUNTRY, countryImportDto.getName(), countryImportDto.getCountryCode())
                                    : INVALID_COUNTRY)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(countryImportDto -> {
                    Country country= modelMapper.map(countryImportDto, Country.class);

                    return country;
                })
                .forEach(this.countryRepository::saveAndFlush);

        return sb.toString();
    }
}
