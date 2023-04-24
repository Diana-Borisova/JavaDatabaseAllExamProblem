package softuni.exam.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
@Validated
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {

    @Size(min = 2, max = 19)
    @Column(nullable = false)
    private String firstName;

    @Size(min = 2, max = 19)
    @Column(nullable = false)
    private String lastName;


    @Column(nullable = false, unique = false)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rating rating;

    @Column(nullable = false)
    private String town;



}
