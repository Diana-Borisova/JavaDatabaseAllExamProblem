package com.softuni.gira.service.impl;

import com.softuni.gira.entity.ClassificationEnum;
import com.softuni.gira.entity.Classification;
import com.softuni.gira.repository.ClassificationRepository;
import com.softuni.gira.service.ClassificationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Getter
@Setter
@Service
public class ClassificationServiceImpl implements ClassificationService {
    private final ClassificationRepository classificationRepository;

    public ClassificationServiceImpl(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

    @Override
    public void classificationInit() {
        if (this.classificationRepository.count() == 0){

            Arrays.stream(ClassificationEnum.values())
                    .forEach(name ->{
                        Classification classification = new Classification();
                       classification.setClassificationEnum(name);
                       classification.setDescription("default");
                        this.classificationRepository.save(classification);
                    });
        }
    }

    @Override
    public Classification findByClassificationName(ClassificationEnum classificationEnum) {
        return this.classificationRepository.findByClassificationEnum(classificationEnum);
    }
}
