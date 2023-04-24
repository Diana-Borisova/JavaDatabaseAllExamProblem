package com.likebookapp.model.views;

import com.likebookapp.model.entity.MoodsEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostViewModel {
    private Long id;

    private String content;

    private MoodsEnum moodsEnum;
    private int likes;
}

