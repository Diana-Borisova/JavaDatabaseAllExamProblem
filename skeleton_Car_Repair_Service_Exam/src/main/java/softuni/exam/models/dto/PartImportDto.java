package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Task;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PartImportDto {

    @Expose
    @NotNull
    @Size(min = 2, max = 19)
    private String partName;

    @Expose
    @NotNull
    @DecimalMin("10")
    @DecimalMax("2000")
    private Double price;

    @Positive
    @Expose
    @NotNull
    private int quantity;

}
