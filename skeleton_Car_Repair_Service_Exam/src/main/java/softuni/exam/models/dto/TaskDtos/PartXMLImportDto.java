package softuni.exam.models.dto.TaskDtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor

@XmlAccessorType(XmlAccessType.FIELD)
public class PartXMLImportDto {
    @NotNull
    @XmlElement(name = "id")
    private Long id;
}
