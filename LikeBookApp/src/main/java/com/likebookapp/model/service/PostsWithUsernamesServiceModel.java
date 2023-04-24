package com.likebookapp.model.service;

import com.likebookapp.model.entity.MoodsEnum;
import com.likebookapp.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class PostsWithUsernamesServiceModel {
    private String content;
    private MoodsEnum mood;
    private String username;
    private int likes;
    private Long id;
    private Set<User> userLikes;
}
