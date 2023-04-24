package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import softuni.exam.models.Car;
import softuni.exam.models.Seller;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {

    @Positive
    @NotNull
    @XmlElement(name = "price")
    private Double price;

    @Size(min = 5)
    @NotNull
    @XmlElement(name = "description")
    private String	description;

    @NotNull
    @XmlElement(name ="has-gold-status")
    private String	hasGoldStatus;

    @NotNull
    @XmlElement(name ="added-on")
    private String addedOn;

    @NotNull
    @XmlElement(name = "seller")
    private OfferSellerImportDto seller;

    @NotNull
    @XmlElement(name = "car")
    private OfferCarImportDto car;
}
