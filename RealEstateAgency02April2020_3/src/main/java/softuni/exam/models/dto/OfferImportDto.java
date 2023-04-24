package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {

    @Positive
    @XmlElement(name ="price")
    @NotNull
    private double price;

    @XmlElement(name = "publishedOn")
    @NotNull
    private String publishedOn;

    @XmlElement(name = "apartment")
    @NotNull
    private OfferApartmentImport apartment;

    @XmlElement(name = "agent")
    @NotNull
    private OfferAgentImport agent;
}
