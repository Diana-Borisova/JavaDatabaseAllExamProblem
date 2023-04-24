package com.softuni.gira.init;


import com.softuni.gira.service.ClassificationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInit implements CommandLineRunner {

    private final ClassificationService classificationService;

    public DataBaseInit(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }


    @Override
    public void run(String... args) throws Exception {
        this.classificationService.classificationInit();
    }
}
