package exam.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "towns")
public class Town extends BaseEntity {

    @Size(min=2)
    @Column(unique = true, nullable = false)
    private String name;

    @Positive
    @Min(1)
    @Column(unique = true, nullable = false)
    private int	population;

    @Size(min=10)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String travelGuide;
}
