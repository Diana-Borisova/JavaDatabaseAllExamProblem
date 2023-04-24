package com.softuni.gira.service;


import com.softuni.gira.entity.Classification;
import com.softuni.gira.entity.ClassificationEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ClassificationService {
    void  classificationInit();

    Classification findByClassificationName(ClassificationEnum classificationEnum);
}
