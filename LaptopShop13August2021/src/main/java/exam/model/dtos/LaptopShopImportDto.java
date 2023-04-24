package exam.model.dtos;

import com.google.gson.annotations.Expose;
import exam.model.Shop;
import exam.model.WarrantyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class LaptopShopImportDto {

    @NotNull
    @Expose
    private String name;

}
