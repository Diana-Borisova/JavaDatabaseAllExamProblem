package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Town;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
public class AgentImportDto {

    @Expose
    @NotNull
    @Size(min = 2)
    private String firstName;

    @Size(min = 2)
    @NotNull
    @Expose
    private String lastName;

    @Email
    @NotNull
    @Expose
    private String email;

    @NotNull
    @Expose
    @Size(min = 2)
    private String town;
}
