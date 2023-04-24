package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class CarImportDto {

    @Size(min=2, max = 19)
    @NotNull
    @Expose
    private String make;

    @Size(min=2, max = 19)
    @NotNull
    @Expose
    private String model;

    @Positive
    @NotNull
    @Expose
    private int kilometers;


    @NotNull
    @Expose
    private String registeredOn;
}
