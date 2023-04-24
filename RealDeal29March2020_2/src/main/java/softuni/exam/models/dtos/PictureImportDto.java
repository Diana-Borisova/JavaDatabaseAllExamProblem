package softuni.exam.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.Car;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class PictureImportDto {
    @Size(min=2, max = 19)
    @NotNull
    @Expose
    private String 	name;


    @NotNull
    @Expose
    private String dateAndTime;

    @NotNull
    @Expose
    private Long car;
}
