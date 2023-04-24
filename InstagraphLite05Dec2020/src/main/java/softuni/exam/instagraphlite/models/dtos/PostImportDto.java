package softuni.exam.instagraphlite.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.instagraphlite.models.BaseEntity;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostImportDto extends BaseEntity {

    @Size(min = 21)
    @XmlElement(name = "caption")
    @NotNull
    private String caption;

    @XmlElement(name = "user")
    @NotNull
    private PostUserImportDto user;

    @XmlElement(name = "picture")
    @NotNull
    private PostPictureImportDto picture;
}
