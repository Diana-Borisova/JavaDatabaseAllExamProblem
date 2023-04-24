package exam.model.dtos;

import exam.model.BaseEntity;
import exam.model.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDto  {

    @Size(min=4)
    @XmlElement(name = "name")
    @NotNull
    private String name;

    @DecimalMin("20000")
    @XmlElement(name = "income")
    @NotNull
    private Double income;

    @Size(min=4)
    @XmlElement(name = "address")
    @NotNull
    private String address;

    @Min(1)
    @Max(50)
    @XmlElement(name = "employee-count")
    @NotNull
    private int employeeCount ;

    @Min(150)
    @XmlElement(name = "shop-area")
    @NotNull
    private int shopArea ;

    @XmlElement(name = "town")
    @NotNull
    private ShopTownImportDto town;
}
