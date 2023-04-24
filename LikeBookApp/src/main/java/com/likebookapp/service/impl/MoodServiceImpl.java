package com.likebookapp.service.impl;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.MoodsEnum;
import com.likebookapp.repository.MoodRepository;
import com.likebookapp.service.MoodService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Arrays;
@Getter
@Setter
@Service
public class MoodServiceImpl implements MoodService {

    private final MoodRepository moodRepository;

    public MoodServiceImpl(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public void initMood() {
        if (moodRepository.count() != 0){
            return;
        }

        Arrays.stream(MoodsEnum.values())
                .forEach(moodsEnum-> {
                    Mood mood = new Mood();
                    mood.setMoodName(moodsEnum);

                    moodRepository.save(mood);
                });

    }

    @Override
    public Mood findByMoodName(MoodsEnum moodsEnum) {
        return this.moodRepository.findByMoodName(moodsEnum).orElse(null);
    }



}

