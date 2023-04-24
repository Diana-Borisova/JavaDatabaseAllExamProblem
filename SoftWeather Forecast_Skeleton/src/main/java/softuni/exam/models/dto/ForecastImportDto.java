package softuni.exam.models.dto;

import lombok.*;
import softuni.exam.models.entity.enums.DaysOfWeek;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastImportDto {


    @NonNull
    @XmlElement(name = "day_of_week")
    private DaysOfWeek dayOfWeek;

    @DecimalMin(value = "-20")
    @DecimalMax(value = "60")
    @NonNull
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;

    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    @NonNull
    @XmlElement(name = "min_temperature")
    private Double minTemperature;

    @NonNull
    @XmlElement
    private String sunrise;

    @NonNull
    @XmlElement
    private String sunset;

    @NonNull
    @XmlElement
    private Long city;


}

