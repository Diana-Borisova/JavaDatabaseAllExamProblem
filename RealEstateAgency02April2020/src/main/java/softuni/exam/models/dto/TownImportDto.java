package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class TownImportDto {
    @Size(min = 2)
    @Expose
    @NotNull
    private String townName;

    @Positive
    @NotNull
    @Min(1)
    @Expose
    private Long population;
}
