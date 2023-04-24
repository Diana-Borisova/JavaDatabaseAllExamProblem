package com.likebookapp.service;

import com.likebookapp.model.entity.MoodsEnum;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.service.PostServiceModel;
import com.likebookapp.model.service.PostsWithUsernamesServiceModel;
import com.likebookapp.model.views.PostViewModel;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostService {
    void addPost(PostServiceModel postServiceModel);

    List<PostViewModel> findAllByMood_MoodName(MoodsEnum moodsEnum);

    int getNumberOfLikes();

    Set<Post> getPostsFromCurrentUser(Long id);

    Set<PostsWithUsernamesServiceModel> getPostsFromOtherUsers(Long id);

    void removeById(Long id);

    void likePostWithId(Long postId, Long userId);

}
