package com.likebookapp.init;

import com.likebookapp.service.MoodService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {
    private final MoodService moodService;

    public DatabaseInit(MoodService moodService) {
        this.moodService = moodService;
    }


    @Override
    public void run(String... args) throws Exception {
        moodService.initMood();
    }
}
