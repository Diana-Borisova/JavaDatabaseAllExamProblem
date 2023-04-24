package com.softuni.gira.entity.view;

import com.softuni.gira.entity.ClassificationEnum;
import com.softuni.gira.entity.ProgressEnum;
import com.softuni.gira.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class TaskViewModel {

    private Long id;
    private String name;


    private String description;

    private LocalDate dueDate;

    private ClassificationEnum classification;

    private ProgressEnum progressEnum;

    private User user;

}
