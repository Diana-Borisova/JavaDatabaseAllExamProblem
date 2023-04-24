package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.entity.enums.DaysOfWeek;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    Set<Forecast> findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DaysOfWeek dayOfWeek, Long city_population);

    Optional<Forecast> findAllByCityAndDayOfWeek(City city, DaysOfWeek dayOfWeek);

  /*  city name, min temperature (to second digit after decimal point), max temperature
    (to second digit after decimal point), sunrise and the sunset of the forecast.
•	Filter only forecasts from sunday and from cities with less than 150000 citizens,
    order them by max temperature in descending order, then by the forecast id in ascending order.*/

}
