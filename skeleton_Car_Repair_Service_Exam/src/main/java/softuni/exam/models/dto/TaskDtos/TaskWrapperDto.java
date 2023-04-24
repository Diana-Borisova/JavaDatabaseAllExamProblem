package softuni.exam.models.dto.TaskDtos;

import lombok.*;
import softuni.exam.models.dto.CarImportDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskWrapperDto {
    @NonNull
    @XmlElement(name = "task")
    private List<TaskImportDto> taskImportDto;
}
