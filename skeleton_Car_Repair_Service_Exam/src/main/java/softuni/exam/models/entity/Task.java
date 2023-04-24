package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {
    @Positive
    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    private Mechanic mechanic;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Car make - %s", getCar().getCarMake())).append(System.lineSeparator())
                .append(String.format("kilometers= %d", getCar().getKilometers())).append(System.lineSeparator());
        sb.append(String.format("-Mechanic: %s %s - task № %d: ", getMechanic().getFirstName(), getMechanic().getLastName(),
                        getId())).append(System.lineSeparator())
                .append(String.format("--Engine: %.2f", getCar().getEngine())).append(System.lineSeparator())
                .append(String.format(" ---Price: %.2f$", getPrice())).append(System.lineSeparator());

        return sb.toString().trim();
    }
}