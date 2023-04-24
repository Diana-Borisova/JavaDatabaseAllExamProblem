package softuni.exam.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "agent")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferAgentImportDto {

   @XmlElement(name = "name")
    private String name;
}
