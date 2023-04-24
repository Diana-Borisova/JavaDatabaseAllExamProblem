package softuni.exam.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    @Column
    private String make;

    @Column
    private String model;

    @Column
    private Integer kilometers;

    @Column
    private LocalDate registeredOn;

    @OneToMany(mappedBy = "car")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "car")
    private List<Offer> offers;

}