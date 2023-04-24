package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@Setter
@Entity
@Table(name = "companies")
public class Company  extends BaseEntity{


    @Column(unique = true,nullable = false)
    private String name;


    @Column(nullable = false)
    private String website;


    @Column(nullable = false)
    private LocalDate dateEstablished  ;

    @ManyToOne(optional = false)
    private Country country;


    @ManyToMany
    @JoinTable(name = "companies_jobs",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "jobs_id", referencedColumnName = "id"))
    private List<Job> jobs;

    public Company() {
        this.jobs = new ArrayList<Job>();
    }
}
