package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity{

    @Size(min=3)
    @Column(nullable = false)
    private String firstName;

    @Size(min=3)
    @Column(nullable = false)
    private String lastName;

    @Size(min=3)
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Positions position;

    @ManyToOne(optional = false)
    private Town town;

    @ManyToOne(optional = false)
    private Stat stat;

    @ManyToOne(optional = false)
    private Team team;

}
