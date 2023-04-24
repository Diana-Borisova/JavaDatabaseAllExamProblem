package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TownImportDto {

    @NotNull
    @Expose
    private String name;

    @NotNull
    @Expose
    private Long population;

}
