package softuni.exam.models.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import softuni.exam.models.Rating;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "seller")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerImportDto {
    @Size(min = 2, max = 19)
    @NonNull
    @XmlElement(name = "first-name")
    private String firstName;

    @Size(min = 2, max = 19)
    @NonNull
    @XmlElement(name = "last-name")
    private String lastName;


    @NonNull
    @XmlElement(name = "email")
    @Email
    private String email;

    @NonNull
    @XmlElement(name = "rating")
    @Enumerated(EnumType.STRING)
    private Rating rating;

    @NonNull
    @XmlElement(name = "town")
    private String town;
}
