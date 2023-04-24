package com.softuni.shopping_list.model.binding;




import com.softuni.shopping_list.model.entity.CategoryEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddBindingModel {

    @NotNull()
    @Size(min = 3, max = 20, message = "Name length must be between 3 and 20 characters!")
    private String name;

    @NotNull()
    @Size(min = 3, message = "Description length must be more than 5 characters!")
    private String description;

    @NotNull
    @Positive(message = "Price must be positive number!")
    private BigDecimal price;

    @FutureOrPresent(message = "The date cannot be in the past!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime neededBefore;

    @Enumerated(EnumType.STRING)
    @NotNull()
    private CategoryEnum category;



}
