package softuni.exam.models.dto.TaskDtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskImportDto {

    @NonNull
    @XmlElement(name= "date")
    private String date;

    @Positive
    @NonNull
    @XmlElement(name= "price")
    private BigDecimal price;

    @NonNull
    @XmlElement(name = "car")
    private CarXmlImportDto car;


    @NonNull
    @XmlElement(name = "mechanic")
    private MechanicXmlImportDto firstName;


    @NonNull
    @XmlElement(name = "part")
    private PartXMLImportDto part;


    @NonNull
    @XmlElement(name = "lastName")
    private String lastName;

}
