package softuni.exam.models.dto.jobsDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Company;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
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
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobImportDto {
    @Size(min=2, max = 40)
    @XmlElement(name = "jobTitle")
    @NotNull
    private String title;

    @DecimalMin("300.00")
    @XmlElement(name = "salary")
    @NotNull
    private double salary;

    @DecimalMin("10.00")
    @XmlElement(name = "hoursAWeek")
    @NotNull
    private double hoursAWeek;

    @Size(min=5)
    @XmlElement(name = "description")
    @NotNull
    private String description;

    @XmlElement(name = "companyId")
    @NotNull
    private Long company;


}
