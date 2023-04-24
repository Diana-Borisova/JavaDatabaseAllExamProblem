package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mechanics")
public class Mechanic extends BaseEntity {
    @Size(min = 2)
    @Column(unique = true, nullable = false)
    private String firstName;

    @Size(min = 2)
    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 2)
    @Column(unique = true, nullable = true)
    private String phone;



    @OneToMany(mappedBy = "mechanic")
    private List<Task> tasks;

}
