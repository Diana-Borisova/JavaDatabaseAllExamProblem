package softuni.exam.instagraphlite.models.dto;

import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "path")
public class PathXmlImportDto {
    @NonNull
    @XmlElement
    private String path;
}
