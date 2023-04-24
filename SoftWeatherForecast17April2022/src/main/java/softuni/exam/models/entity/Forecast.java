package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "forecasts")
public class Forecast extends  BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @DecimalMin("-20")
    @DecimalMax("60")
    @Column(nullable = false)
    private double maxTemperature;

    @DecimalMin("-50")
    @DecimalMax("40")
    @Column(nullable = false)
    private double minTemperature;

    @Column(nullable = false)
    private LocalTime sunrise;


    @Column(nullable = false)
    private LocalTime sunset;

    @ManyToOne(optional = false)
    private City city;



}
