package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class MechanicImportDto {

    @Expose
    @NotNull
    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    @Expose
    @NotNull
    private String lastName;

    @Email
    @Expose
    @NotNull
    private String email;

}
