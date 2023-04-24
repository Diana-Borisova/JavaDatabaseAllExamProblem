package com.likebookapp.service;

import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.MoodsEnum;

import java.util.Optional;

public interface MoodService {
    void initMood();


    Mood findByMoodName(MoodsEnum moodsEnum);
}
