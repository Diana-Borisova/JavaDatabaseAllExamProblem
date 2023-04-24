package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CountryImportDto {

    @Expose
    @NotNull
    @Size(min=2, max = 30)
    private String name;

    @Size(min=2, max = 19)
    @Expose
    @NotNull
    private String countryCode;

    @Size(min=2, max = 19)
    @Expose
    @NotNull
    private String currency;
}
