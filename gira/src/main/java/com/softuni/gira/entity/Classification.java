package com.softuni.gira.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "classifications")
public class Classification extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassificationEnum classificationEnum;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

}
