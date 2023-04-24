package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "parts")
public class Part extends BaseEntity{
    @Size(min = 2, max = 19)
    @Column(unique = true, nullable = false)
    private String partName;

    @DecimalMin("10")
    @DecimalMax("2000")
    @Column( nullable = false)
    private Double price;

    @Positive
    @Column(nullable = false)
    private int quantity;

    @OneToMany(mappedBy = "part")
    private List<Task> tasks;

}
