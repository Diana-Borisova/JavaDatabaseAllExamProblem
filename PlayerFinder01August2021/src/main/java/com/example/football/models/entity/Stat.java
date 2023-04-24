package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "stats")
public class Stat extends BaseEntity{

    @Positive
    @Column(nullable = false)
    private float shooting;

    @Positive
    @Column(nullable = false)
    private float passing;

    @Positive
    @Column(nullable = false)
    private float endurance;
}
