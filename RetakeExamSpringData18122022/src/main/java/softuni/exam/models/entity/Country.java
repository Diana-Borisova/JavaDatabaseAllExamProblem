package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country extends BaseEntity {

    @Column(unique = true,nullable = false)
    private String name;


    @Column(unique = true,nullable = false)
    private String code;


    @Column(nullable = false)
    private String currency;
}
