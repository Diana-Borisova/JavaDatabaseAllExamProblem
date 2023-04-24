package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {
    @Positive
    @NotNull
    @XmlElement(name = "price")
    private Double price;

    @NotNull
    @XmlElement(name = "publishedOn")
    private String publishedOn;

    @NotNull
    @XmlElement(name = "apartment")
    private OfferApartmentDto apartment;

    @NotNull
    @XmlElement(name = "agent")
    private OfferAgentDto agent;
}
