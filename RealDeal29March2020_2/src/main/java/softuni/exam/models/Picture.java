package softuni.exam.models;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {

    @Size(min=2, max = 19)
    @Column(unique = true,nullable = false)
    private String 	name;


    @Column(nullable = false)
    private LocalDateTime dateAndTime;

    @ManyToOne(optional = false)
    private Car car;

    @ManyToMany(mappedBy = "pictures")
    private List<Offer> offers;
}
