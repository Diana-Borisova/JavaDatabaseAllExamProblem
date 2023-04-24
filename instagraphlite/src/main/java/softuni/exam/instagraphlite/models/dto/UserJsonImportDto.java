package softuni.exam.instagraphlite.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import softuni.exam.instagraphlite.models.entity.Picture;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
public class UserJsonImportDto {
    @Expose
    @Size(min = 2, max = 18)
    @NonNull
    private String username;

    @Size(min = 4)
    @NotNull
    @Expose
    private String password;

    @NotNull
    @Expose
    private String profilePicture;

}
