package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "car_type", nullable = false)
    private CarType carType;

    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String carMake;

    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String carModel;

    @Positive
    @Column(nullable = false)
    private int year;

    @Size(min = 2, max = 30)
    @Column(unique = true, nullable = false)
    private String plateNumber;

    @Positive
    @Column(nullable = false)
    private int kilometers;

    @DecimalMin("1.00")
    @Column(nullable = false)
    private Double engine;


    @OneToMany(mappedBy = "car")
    private List<Task> tasks;

    public Car() {
        this.tasks = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Car make - %s", carMake)).append(System.lineSeparator())
                .append(String.format("kilometers= %d", kilometers)).append(System.lineSeparator());
        for (Task task : this.tasks) {
            sb.append(String.format("-Mechanic: %s %s - task № %d: ", task.getMechanic().getFirstName(), task.getMechanic().getLastName(),
                            task.getId())).append(System.lineSeparator())
                    .append(String.format("--Engine: %.2f", task.getCar().getEngine())).append(System.lineSeparator())
                    .append(String.format(" ---Price: %.2f$", task.getPrice())).append(System.lineSeparator());
        }
        return sb.toString().trim();

    }
}