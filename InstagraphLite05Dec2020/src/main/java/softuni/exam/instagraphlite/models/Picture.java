package softuni.exam.instagraphlite.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String path;

    @DecimalMin("500")
    @DecimalMax("60000")
    @Column( nullable = false)
    private Double size;


}
