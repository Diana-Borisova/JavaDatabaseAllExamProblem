package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@Setter
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity{


    @Column(nullable = false)
    private String title;

    @DecimalMin("300.00")
    @Column(nullable = false)
    private double salary;

    @DecimalMin("10.00")
    @Column(nullable = false)
    private double hoursAWeek;

    @Size(min=5)
    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;

@ManyToOne(optional = false)
private Company company;


    @ManyToMany(mappedBy = "jobs", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Company> companies;
    public Job() {
        this.companies = new ArrayList<Company>();
    }

}
