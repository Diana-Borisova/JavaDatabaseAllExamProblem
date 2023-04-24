package softuni.exam.instagraphlite.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String path;

    @Min(500)
    @Max(60000)
    @Column( nullable = false)
    private Double size;

    @Override
    public String toString() {
        return  size + " - " + path + System.lineSeparator();
    }
}
