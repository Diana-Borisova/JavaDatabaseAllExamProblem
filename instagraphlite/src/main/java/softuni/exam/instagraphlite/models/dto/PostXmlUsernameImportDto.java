package softuni.exam.instagraphlite.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@XmlRootElement(name = "username")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostXmlUsernameImportDto {
    @NotNull
    @XmlElement
    @Size(min = 2, max = 18)
    private String username;
}
