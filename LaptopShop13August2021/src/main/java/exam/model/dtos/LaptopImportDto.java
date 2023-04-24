package exam.model.dtos;

import com.google.gson.annotations.Expose;
import exam.model.BaseEntity;
import exam.model.Shop;
import exam.model.WarrantyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class LaptopImportDto  {

    @Size(min=8)
    @NotNull
    @Expose
    private String macAddress;

    @Positive
    @NotNull
    @Expose
    private double cpuSpeed;

    @Min(8)
    @Max(128)
    @NotNull
    @Expose
    private int ram;

    @Min(128)
    @Max(1024)
    @NotNull
    @Expose
    private int storage;

    @Size(min=10)
    @NotNull
    @Expose
    private String description;

    @Positive
    @NotNull
    @Expose
    private double price;


    @NotNull
    @Expose
    private WarrantyType warrantyType;

    @NotNull
    @Expose
    private LaptopShopImportDto shop;

}
