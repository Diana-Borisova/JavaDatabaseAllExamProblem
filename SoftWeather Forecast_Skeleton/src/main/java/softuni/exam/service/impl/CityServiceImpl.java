package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Messages.INVALID_CITY;
import static softuni.exam.constants.Messages.VALID_CITY_FORMAT;
import static softuni.exam.constants.Paths.CITIES_PATH;


@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;

    private final CountryService countryService;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils, CountryService countryService) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.countryService = countryService;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count()>0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITIES_PATH));
    }

    @Override
    public String importCities() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson
                        .fromJson(readCitiesFileContent(), CityImportDto[].class))
                        .filter(cityImportDto -> {
                    boolean isValid = this.validationUtils.isValid(cityImportDto);
                    Optional<City> cityByName = this.cityRepository.findCityByCityName(cityImportDto.getCityName());
                    if (cityByName.isPresent()) {
                        isValid = false;
                    }
                    sb.append(isValid
                            ? String.format(VALID_CITY_FORMAT,
                            cityImportDto.getCityName(),
                            cityImportDto.getPopulation())
                            : INVALID_CITY)
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(cityImportDto -> {
                    City city = modelMapper.map(cityImportDto, City.class);
                    Optional<Country> countryById = this.countryService.getCountryById(cityImportDto.getCountry());
                    if (countryById.isEmpty()){
                        System.out.println("ERROR: " + cityImportDto.getCityName());
                        return city;
                    }
                    city.setCountry(countryById.get());
                    return city;
                })
                .forEach(this.cityRepository::save);

        return sb.toString();
    }

    @Override
    public City findCityById(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }


}
