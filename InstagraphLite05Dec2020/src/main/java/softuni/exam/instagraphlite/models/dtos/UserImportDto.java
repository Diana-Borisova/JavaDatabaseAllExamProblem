package softuni.exam.instagraphlite.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.instagraphlite.models.BaseEntity;
import softuni.exam.instagraphlite.models.Picture;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserImportDto {

    @Size(min = 2, max = 18)
    @NotNull
    @Expose
    private String username;

    @Size(min = 4)
    @NotNull
    @Expose
    private String password;


    @NotNull
    @Expose
    private String profilePicture;
}
