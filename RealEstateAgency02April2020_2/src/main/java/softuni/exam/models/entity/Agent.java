package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agents")
public class Agent extends BaseEntity {
    @Size(min=2)
    @Column(unique = true, nullable = false)
    private String firstName ;

    @Size(min=2)
    @Column( nullable = false)
    private String lastName ;

    @Email
    @Column(unique = true, nullable = false)
    private String email ;

    @ManyToOne(optional = false)
    private Town town;

}
