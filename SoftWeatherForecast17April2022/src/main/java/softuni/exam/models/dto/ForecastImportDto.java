package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.BaseEntity;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {

    @Enumerated(EnumType.STRING)
    @NotNull
    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @DecimalMin("-20")
    @DecimalMax("60")
    @NotNull
    @XmlElement(name = "max_temperature")
    private double maxTemperature;

    @DecimalMin("-50")
    @DecimalMax("40")
    @NotNull
    @XmlElement(name = "min_temperature")
    private double minTemperature;

    @NotNull
    @XmlElement(name = "sunrise")
    private String sunrise;


    @NotNull
    @XmlElement(name = "sunset")
    private String sunset;

    @NotNull
    @XmlElement(name = "city")
    private Long city;



}
