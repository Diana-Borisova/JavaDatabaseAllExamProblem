package com.softuni.gira.entity.binding;

import com.softuni.gira.entity.Classification;
import com.softuni.gira.entity.ClassificationEnum;
import com.softuni.gira.entity.ProgressEnum;
import com.softuni.gira.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAddBindingModel {

    @NotNull(message = "Username cannot be empty")
    @Size(min=3, max = 20, message = "Name length must be between 3 and 20 characters (inclusive 3 and 20).")
    private String name;

    @NotNull(message = "Description cannot be empty")
    @Size(min=5, message = "Description length must be more than 5 characters!")
    private String description;


    @NotNull
    @FutureOrPresent(message = "The date cannot be in the past!")
    private LocalDate dueDate;

    @NotNull(message = "Classification cannot be null!")
    @Enumerated(EnumType.STRING)
    private ClassificationEnum classification;


    private ProgressEnum progressEnum;


}
