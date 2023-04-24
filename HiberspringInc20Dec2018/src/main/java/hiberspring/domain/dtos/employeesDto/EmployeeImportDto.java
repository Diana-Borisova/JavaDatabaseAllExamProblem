package hiberspring.domain.dtos.employeesDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeImportDto {
    @NotNull
    @XmlAttribute(name = "first-name")
    private String firstName;

    @NotNull
    @XmlAttribute(name = "last-name")
    private String lastName;

    @NotNull
    @XmlAttribute(name = "position")
    private String position;

    @NotNull
    @XmlElement(name = "card")
    private String employeeCard;

    @NotNull
    @XmlElement(name="branch")
    private String branch;
}
