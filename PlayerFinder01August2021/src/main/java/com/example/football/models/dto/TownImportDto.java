package com.example.football.models.dto;

import com.example.football.models.entity.BaseEntity;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class TownImportDto {

    @Size(min=2)
    @NotNull
    @Expose
    private String name;

    @Positive
    @Min(1)
    @NotNull
    @Expose
    private int population;

    @Size(min=10)
    @NotNull
    @Expose
    private String travelGuide ;


}
