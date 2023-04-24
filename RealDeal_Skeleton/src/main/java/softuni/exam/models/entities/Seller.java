package softuni.exam.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import softuni.exam.util.Enums.Rating;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity{

    @Column(name = "first_name")
    @Size(min = 2, max = 19)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2, max = 19)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Column(nullable = false)
    private String town;

    @OneToMany(mappedBy = "seller")
    private List<Offer> offers;
}