package exam.model.dtos;

import com.google.gson.annotations.Expose;
import exam.model.BaseEntity;
import exam.model.Town;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CustomerImportDto {

    @Size(min=2)
    @NotNull
    @Expose
    private String firstName;

    @Size(min=2)
    @NotNull
    @Expose
    private String lastName;

    @Email
    @NotNull
    @Expose
    private String email;

    @NotNull
    @Expose
    private String registeredOn;

    @NotNull
    @Expose
    private CustomerTownImportDto town;

}
