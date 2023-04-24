package softuni.exam.instagraphlite.models.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.instagraphlite.models.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PictureImportDto  {

    @NotNull
    @Expose
    private String path;

    @DecimalMin("500")
    @DecimalMax("60000")
    @NotNull
    @Expose
    private BigDecimal size;
}
