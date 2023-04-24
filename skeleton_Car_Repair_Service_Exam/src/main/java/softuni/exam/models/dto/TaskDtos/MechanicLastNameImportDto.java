package softuni.exam.models.dto.TaskDtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

@Getter
@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class MechanicLastNameImportDto {

    @NotNull
    @Size(min = 2)
    @XmlElement(name = "lastName")
    private String lastName;
}
