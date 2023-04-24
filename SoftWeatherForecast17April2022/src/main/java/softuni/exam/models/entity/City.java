package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cities")
public class City extends BaseEntity {

    @Size(min=2, max = 60)
    @Column(unique = true, nullable = false)
    private String cityName;

    @Size(min=2)
    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;

    @Min(500)
    @Column( nullable = false)
    private int population;

    @ManyToOne(optional = false)
    private Country country;


}
