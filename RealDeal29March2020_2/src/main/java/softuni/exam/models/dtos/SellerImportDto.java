package softuni.exam.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.Ratings;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerImportDto {
    @Size(min=2, max = 19)
    @XmlElement(name = "first-name")
    @NotNull
    private String 	firstName;

    @Size(min=2, max = 19)
    @XmlElement(name = "last-name")
    @NotNull
    private String 	lastName;

    @Email
    @XmlElement(name = "email")
    @NotNull
    private String 	email;

    @XmlElement(name = "rating")
    @NotNull
    private Ratings rating;


    @XmlElement(name = "town")
    @NotNull
    private String 	town;
}
