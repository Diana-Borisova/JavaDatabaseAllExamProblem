package com.softuni.gira.repository;


import com.softuni.gira.entity.Classification;
import com.softuni.gira.entity.ClassificationEnum;
import com.softuni.gira.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification,Long> {
    Classification findByClassificationEnum(ClassificationEnum classificationEnum);
}
