package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.BaseEntity;
import softuni.exam.models.entity.Town;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter

public class AgentImportDto {
    @Size(min=2)
    @NotNull
    @Expose
    private String firstName ;

    @Size(min=2)
    @NotNull
    @Expose
    private String lastName ;

    @Email
    @NotNull
    @Expose
    private String email ;

    @NotNull
    @Expose
    private String town;

}
