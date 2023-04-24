package softuni.exam.instagraphlite.models.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostXmlImportDto {
    @NonNull
    @XmlElement
    @Size(min = 21)
    private String caption;


    @NonNull
    @XmlElement()
    private PostXMLUserImportDto user;


    @NonNull
    @XmlElement
    private PictureXmlImportDto picture;

}
