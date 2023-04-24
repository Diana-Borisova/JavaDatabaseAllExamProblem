package softuni.exam.models.dto.companiesDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Job;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyImportDto {
    @Size(min=2, max = 40)
    @XmlElement(name = "companyName")
    @NotNull
    private String name;

    @Size(min=2, max = 30)
    @XmlElement(name = "website")
    @NotNull
    private String website;


    @XmlElement(name = "dateEstablished")
    @NotNull
    private String dateEstablished  ;

    @XmlElement(name = "countryId")
    @NotNull
    private Long country;


}
