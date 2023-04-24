package com.softuni.gira.entity.services;

import com.softuni.gira.entity.Classification;
import com.softuni.gira.entity.ClassificationEnum;
import com.softuni.gira.entity.ProgressEnum;
import com.softuni.gira.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TaskServiceModel {

    private Long id;

    private String name;

    private String description;

    private ProgressEnum progressEnum;

    private LocalDate dueDate;

    private ClassificationEnum classification;

    private User user;
}
