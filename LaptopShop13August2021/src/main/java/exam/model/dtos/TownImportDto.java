package exam.model.dtos;

import com.google.gson.annotations.Expose;
import exam.model.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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
@XmlRootElement(name = "town")
@XmlAccessorType(XmlAccessType.FIELD)
public class TownImportDto {

    @Size(min=2)
    @XmlElement(name = "name")
    @NotNull
    private String name;

    @Positive
    @Min(1)
    @XmlElement(name = "population")
    @NotNull
    private int	population;

    @Size(min=10)
    @XmlElement(name = "travel-guide")
    @NotNull
    private String travelGuide;
}
