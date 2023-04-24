package exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity {

    @Size(min=4)
    @Column(unique = true, nullable = false)
    private String name;

    @DecimalMin("20000")
    @Column( nullable = false)
    private Double income;

    @Size(min=4)
    @Column( nullable = false)
    private String address;

    @Min(1)
    @Max(50)
    @Column( nullable = false)
    private int employeeCount ;

    @Min(150)
    @Column( nullable = false)
    private int shopArea ;

    @ManyToOne(optional = false)
    private Town town;
}
