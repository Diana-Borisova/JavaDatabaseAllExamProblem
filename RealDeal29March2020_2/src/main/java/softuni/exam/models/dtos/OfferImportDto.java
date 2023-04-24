package softuni.exam.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.config.ApplicationBeanConfiguration;
import softuni.exam.models.Car;
import softuni.exam.models.Picture;
import softuni.exam.models.Seller;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {
    @Positive
    @XmlElement(name = "price")
    @NotNull
    private Double price;

    @Size(min=5)
    @XmlElement(name = "description")
    @NotNull
    private String 	description;

    @XmlElement(name = "added-on")
    @NotNull
    private String addedOn;

    @XmlElement(name = "car")
    @NotNull
    private OfferCarImportDto car;

    @XmlElement(name = "seller")
    @NotNull
    private OfferSellerImportDto seller;

    @XmlElement(name = "has-gold-status")
    @NotNull
    private boolean hasGoldStatus;


}
