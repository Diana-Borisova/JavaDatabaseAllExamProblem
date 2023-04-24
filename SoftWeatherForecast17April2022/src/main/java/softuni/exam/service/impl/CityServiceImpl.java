package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CityImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static softuni.exam.constants.Paths.CITIES_PATH;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtils validationUtils;

    public CityServiceImpl(CityRepository cityRepository, CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.cityRepository = cityRepository;
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
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
        Arrays.stream(gson.fromJson(readCitiesFileContent(), CityImportDto[].class))
                .filter(cityImportDto -> {
                    boolean isValid = validationUtils.isValid(cityImportDto);
                    Optional<City>city = this.cityRepository.findCityByCityName(cityImportDto.getCityName());
                    Optional<Country> country = this.countryRepository.findById(cityImportDto.getCountry());
                    if (city.isPresent() || country.isEmpty() ){
                        isValid = false;
                    }
                    return isValid;
                })
                .map(cityImportDto -> {
                    City city = modelMapper.map(cityImportDto, City.class);
                    Country country = this.countryRepository.findById(cityImportDto.getCountry()).orElse(null);
                    city.setCountry(country);
                    return city;
                })
                .forEach(this.cityRepository::save);

        return sb.toString().trim();
    }
}
