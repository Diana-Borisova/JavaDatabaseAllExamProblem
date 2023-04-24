package softuni.exam.models;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity{

    @Size(min=2, max = 19)
    @Column(nullable = false)
    private String 	firstName;

    @Size(min=2, max = 19)
    @Column(nullable = false)
    private String 	lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String 	email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ratings rating;


    @Column(nullable = false)
    private String 	town;
}
