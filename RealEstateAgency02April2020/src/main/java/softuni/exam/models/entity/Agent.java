package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "agents")
public class Agent extends BaseEntity{

    @Size(min = 2)
    @Column(unique = true, nullable = false)
    private String firstName;

    @Size(min = 2)
    @Column( nullable = false)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "town_id")
    private Town town;
}
