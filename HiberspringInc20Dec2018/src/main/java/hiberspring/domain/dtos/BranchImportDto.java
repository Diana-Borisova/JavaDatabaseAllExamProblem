package hiberspring.domain.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BranchImportDto {

    @NotNull
    @Expose
    private String name;

    @NotNull
    @Expose
    private String town;

}
