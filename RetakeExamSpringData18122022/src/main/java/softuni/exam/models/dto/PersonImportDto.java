package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.BaseEntity;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.StatusType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PersonImportDto  {

    @Size(min=2, max = 30)
    @Expose
    @NotNull
    private String firstName;

    @Size(min=2, max = 30)
    @Expose
    @NotNull
    private String lastName;

    @Email
    @Expose
    @NotNull
    private String email;

    @Size(min=2, max = 13)
    @Expose
    @NotNull
    private String phone;

    @Expose
    @NotNull
    private StatusType statusType;

    @Expose
    @NotNull
    private Long country;

}
