package hiberspring.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_cards")
public class EmployeeCard  extends BaseEntity{


    @Column(nullable = false, unique = true)
    private String number;

    @OneToMany
    @JoinColumn(name = "employee_card_id")
    private List<Employee> employees;
}
