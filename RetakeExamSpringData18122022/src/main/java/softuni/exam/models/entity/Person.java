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
@Table(name = "people")
public class Person extends BaseEntity{


    @Column(unique = true,nullable = false)
    private String firstName;


    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(unique = true,nullable = false)
    private String email;


    @Column(unique = true,nullable = true)
    private String phone;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private StatusType statusType;

    @ManyToOne(optional = false)
    private Country country;

}
