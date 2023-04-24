package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.VALID_COUNTRY_FORMAT;
import static softuni.exam.constants.Paths.COUNTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final Gson gson;

   private final ValidationUtils validationUtils;

   private final ModelMapper modelMapper;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
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
        final StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readCountriesFromFile(), CountryImportDto[].class))
                .filter(countryDto -> {
                    boolean isValid = validationUtils.isValid(countryDto);

                    Optional<Country> countryByCountryName = countryRepository.findCountryByCountryName(countryDto.getCountryName());
                    if (countryByCountryName.isPresent()){
                        isValid = false;
                    }

                    sb.append(isValid
                                    ? String.format(VALID_COUNTRY_FORMAT, countryDto.getCountryName()
                                    , countryDto.getCurrency())
                                    : INVALID_COUNTRY)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(countrySeedDto -> modelMapper.map(countrySeedDto, Country.class))
                .forEach(countryRepository::save);

        return sb.toString();
    }

    @Override
    public Optional<Country> getCountryById(Long countryId) {
        return this.countryRepository.findById(countryId);
    }
}

