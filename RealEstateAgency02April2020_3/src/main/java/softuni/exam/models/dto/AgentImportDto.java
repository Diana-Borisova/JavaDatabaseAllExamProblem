package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.Town;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AgentImportDto {
    @Size(min=2)
    @Expose
    @NotNull
    private String firstName;

    @Size(min=2)
    @Expose
    @NotNull
    private String lastName;

    @Email
    @Expose
    @NotNull
    private String email;

    @Expose
    @NotNull
    private String town;
}
