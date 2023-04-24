package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CountryImportDto  {

    @Size(min=2, max = 60)
    @NotNull
    @Expose
    private String countryName;

    @Size(min=2, max = 20)
    @NotNull
    @Expose
    private String currency;

}
