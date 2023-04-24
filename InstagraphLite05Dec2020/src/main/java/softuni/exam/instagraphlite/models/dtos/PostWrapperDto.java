package softuni.exam.instagraphlite.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.instagraphlite.models.BaseEntity;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.User;

import javax.persistence.*;
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
@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostWrapperDto extends BaseEntity {

    @Size(min = 21)
    @XmlElement(name = "post")
    @NotNull
    private List<PostImportDto> postImportDto;


}
