package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastImportDto;
import softuni.exam.models.dto.ForecastImportWrapperDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.entity.enums.DaysOfWeek;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static softuni.exam.constants.Messages.INVALID_FORECAST;
import static softuni.exam.constants.Messages.VALID_FORECAST_FORMAT;
import static softuni.exam.constants.Paths.FORECASTS_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtils validationUtils;
    private final CityService cityService;
    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtils validationUtils, CityService cityService) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtils = validationUtils;
        this.cityService = cityService;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count()>0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_PATH));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(FORECASTS_PATH, ForecastImportWrapperDto.class)
                .getForecastImportDto()
                .stream()
                .filter(forecastSeedDto -> {
                    boolean isValid = validationUtils.isValid(forecastSeedDto);

                    City city = cityService.findCityById(forecastSeedDto.getCity());

                    if (city == null) {
                        isValid = false;
                    }

                    Forecast forecast = forecastRepository.findAllByCityAndDayOfWeek(city, forecastSeedDto.getDayOfWeek()).orElse(null);
                    if (forecast != null || forecastSeedDto.getDayOfWeek() == null) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format("Successfully import forecast %s - %.2f",
                                    forecastSeedDto.getDayOfWeek(), forecastSeedDto.getMaxTemperature())
                                    : "Invalid forecast")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(forecastSeedDto -> {
                    Forecast forecast = modelMapper.map(forecastSeedDto, Forecast.class);

                    City city = cityService.findCityById(forecastSeedDto.getCity());
//
                    forecast.setCity(city);

                    return forecast;
                })
                .forEach(forecastRepository::save);

        return sb.toString();
    }

    @Override
    public String exportForecasts() {
        StringBuilder sb = new StringBuilder();
       Set<Forecast> forecasts = this.forecastRepository
               .findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DaysOfWeek.SUNDAY, 150000L);
       forecasts.forEach(forecast ->{
           sb.append(String.format("City: %s\n" +
                               "-min temperature: %.2f\n" +
                               "--max temperature: %.2f\n" +
                               "---sunrise: %s\n" +
                               "-----sunset: %s",
                       forecast.getCity().getCityName(),
                       forecast.getMinTemperature(),
                       forecast.getMaxTemperature(),
                       forecast.getSunrise(),
                       forecast.getSunset()))
               .append(System.lineSeparator());
    });

        return sb.toString().trim();
    }
}
