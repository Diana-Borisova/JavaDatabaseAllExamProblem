package softuni.exam.models.dto;

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
    @Expose
    @Size(min = 2, max = 19)
    @NotNull
    private String make;

    @Size(min = 2, max = 19)
    @NotNull
    @Expose
    private String model;

    @Positive
    @NotNull
    @Expose
    private Integer	kilometers;

    @NotNull
    @Expose
    private String registeredOn;
}
