package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.BaseEntity;
import softuni.exam.models.entity.Country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter

public class CityImportDto {

    @Size(min=2, max = 60)
    @NotNull
    @Expose
    private String cityName;

    @Size(min=2)
    @NotNull
    @Expose
    private String description;

    @Min(500)
    @NotNull
    @Expose
    private int population;

    @NotNull
    @Expose
    private Long country;

}
