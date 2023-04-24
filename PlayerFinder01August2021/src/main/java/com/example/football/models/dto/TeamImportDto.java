package com.example.football.models.dto;

import com.example.football.models.entity.BaseEntity;
import com.example.football.models.entity.Town;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class TeamImportDto  {
    @Size(min=3)
    @NotNull
    @Expose
    private String name;

    @Size(min=3)
    @NotNull
    @Expose
    private String stadiumName;

    @Min(1000)
    @NotNull
    @Expose
    private int fanBase;

    @Size(min=10)
    @NotNull
    @Expose
    private String history ;

    @NotNull
    @Expose
    @Size(min=2)
    private String townName;
}
