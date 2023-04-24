package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import softuni.exam.models.entity.CarType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportDto {

    @Size(min = 2, max = 30)
    @NonNull
    @XmlElement
    private String carMake;

    @Size(min = 2, max = 30)
    @NonNull
    @XmlElement
    private String carModel;

    @Positive
    @NonNull
    @XmlElement
    private int year;

    @Size(min = 2, max = 30)
    @NonNull
    @XmlElement
    private String plateNumber;

    @Positive
    @NonNull
    @XmlElement
    private int	kilometers;

    @DecimalMin("1.00")
    @NonNull
    @XmlElement
    private Double engine;

    @NonNull
    @XmlElement
    private CarType carType;

}
