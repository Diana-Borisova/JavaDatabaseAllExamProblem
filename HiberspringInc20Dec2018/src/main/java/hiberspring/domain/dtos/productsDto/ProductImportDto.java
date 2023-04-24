package hiberspring.domain.dtos.productsDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportDto {
    @NotNull
    @XmlAttribute(name = "name")
    private String name;

    @NotNull
    @XmlAttribute(name = "clients")
    private Integer clients;

    @NotNull
    @XmlElement(name="branch")
    private String branch;
}
