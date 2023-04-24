package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PictureImportDto {
    @Expose
    @NotNull
    @Size(min = 2, max = 19)
    private String name;

    @Expose
    @NotNull
    private String dateAndTime;

    @Expose
    @NotNull
    private Long car;
}
