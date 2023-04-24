package softuni.exam.instagraphlite.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PictureJsonImportDto {

    @Expose
    @NotNull
    private String path;

    @Expose
    @Min(500)
    @Max(60000)
    @NotNull
    private Double size;
}
