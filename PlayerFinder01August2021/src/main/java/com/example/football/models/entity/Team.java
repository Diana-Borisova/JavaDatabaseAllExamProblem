package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team extends BaseEntity{
    @Size(min=3)
    @Column(unique = true, nullable = false)
    private String name;

    @Size(min=3)
    @Column(nullable = false)
    private String stadiumName;

    @Min(1000)
    @Column(nullable = false)
    private int fanBase;

    @Size(min=10)
    @Column(columnDefinition = "TEXT",nullable = false)
    private String history ;

    @ManyToOne(optional = false)
    private Town town;
}
