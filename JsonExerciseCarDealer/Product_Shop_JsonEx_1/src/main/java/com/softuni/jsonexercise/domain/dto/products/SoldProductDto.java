package com.softuni.jsonexercise.domain.dto.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SoldProductDto {
    private String name;
    private BigDecimal price;
    private String buyerFirstName;
    private String buyerLastName;

}
