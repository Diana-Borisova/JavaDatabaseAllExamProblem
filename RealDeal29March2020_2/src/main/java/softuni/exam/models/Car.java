package softuni.exam.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @Size(min=2, max = 19)
    @Column(nullable = false)
    private String make;

    @Size(min=2, max = 19)
    @Column(nullable = false)
    private String model;

    @Positive
    @Column(nullable = false)
    private int kilometers;


    @Column(nullable = false)
    private LocalDate	registeredOn;

    @OneToMany(mappedBy = "car", targetEntity = Picture.class, fetch = FetchType.EAGER)
    private List<Picture>pictures;
}
