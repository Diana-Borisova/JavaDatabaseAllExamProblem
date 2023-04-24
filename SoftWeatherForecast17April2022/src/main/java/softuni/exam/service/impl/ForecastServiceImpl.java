package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unbescape.css.CssIdentifierEscapeType;
import softuni.exam.models.dto.ForecastImportDto;
import softuni.exam.models.dto.ForecastWrapperDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
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
import static softuni.exam.constants.Messages.VALID_FORECAST;
import static softuni.exam.constants.Paths.FORECASTS_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {
    private final ForecastRepository forecastRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtils validationUtils;
    private final XmlParser xmlParser;

    public ForecastServiceImpl(ForecastRepository forecastRepository, CityRepository cityRepository, ModelMapper modelMapper, ValidationUtils validationUtils, XmlParser xmlParser) {
        this.forecastRepository = forecastRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
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
                .fromFile(FORECASTS_PATH, ForecastWrapperDto.class)
                .getForecastImportDto()
                .stream()
                .filter(forecastImportDto -> {
                    boolean isValid = validationUtils.isValid(forecastImportDto);
                    City  city = this.cityRepository.findById(forecastImportDto.getCity()).orElse(null);
                  Optional<  Forecast> forecast = this.forecastRepository.findByCityAndDayOfWeek(forecastImportDto.getDayOfWeek(),city);

                    if (forecast.isPresent()|| city== null|| forecastImportDto.getDayOfWeek()==null) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format(VALID_FORECAST,
                                    forecastImportDto.getDayOfWeek(),forecastImportDto.getMaxTemperature())
                                    : INVALID_FORECAST)
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(forecastImportDto -> {
                    Forecast forecast= modelMapper.map(forecastImportDto, Forecast.class);
                   City city = this.cityRepository.findById(forecastImportDto.getCity()).orElse(null);
                    forecast.setCity(city);
                    return forecast;
                })
                .forEach(this.forecastRepository::save);

        return sb.toString();
    }
    @Override
    public String exportForecasts() {
        StringBuilder sb = new StringBuilder();
List<Forecast> forecasts = this.forecastRepository.findAllByDayOfWeekAndCity_PopulationOrderByMaxTemperatureDescIdAsc(DayOfWeek.SUNDAY, 150000);
        for (Forecast forecast :forecasts) {
            sb.append(String.format("City: %s", forecast.getCity().getCityName())).append(System.lineSeparator());
            sb.append(String.format("   -min temperature: %.2f", forecast.getMinTemperature())).append(System.lineSeparator());
            sb.append(String.format("   --max temperature: %.2f", forecast.getMaxTemperature())).append(System.lineSeparator());
            sb.append(String.format("   ---sunrise: " + forecast.getSunrise())).append(System.lineSeparator());
            sb.append(String.format("   ----sunset: " + forecast.getSunset())).append(System.lineSeparator());

        }
        return sb.toString().trim();
    }
}
