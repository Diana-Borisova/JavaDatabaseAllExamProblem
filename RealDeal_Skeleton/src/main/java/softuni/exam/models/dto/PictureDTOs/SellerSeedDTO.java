package softuni.exam.models.dto.PictureDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.util.Enums.Rating;

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
public class SellerSeedDTO {

    @XmlElement(name = "first-name")
    @Size(min = 2, max = 19)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 2, max = 19)
    private String lastName;

    @XmlElement
    @Email
    private String email;

    @XmlElement
    @Enumerated(value = EnumType.STRING)
    private Rating rating;

    @XmlElement
    @NotNull
    private String town;
}