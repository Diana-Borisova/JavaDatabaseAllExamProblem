package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.BaseEntity;
import softuni.exam.models.entity.Town;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "apartment")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentImportDto extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @XmlElement(name = "apartmentType")
    @NotNull
    private ApartmentType apartmentType;

    @DecimalMin("40.00")
    @XmlElement(name = "area")
    @NotNull
    private double area;

    @XmlElement(name = "town")
    @NotNull
    private String town;
}
