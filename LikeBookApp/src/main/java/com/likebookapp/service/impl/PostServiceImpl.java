package com.likebookapp.service.impl;

import com.likebookapp.model.entity.MoodsEnum;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.model.service.PostServiceModel;
import com.likebookapp.model.service.PostsWithUsernamesServiceModel;
import com.likebookapp.model.views.PostViewModel;
import com.likebookapp.repository.PostRepository;
import com.likebookapp.service.MoodService;
import com.likebookapp.service.PostService;
import com.likebookapp.service.UserService;
import com.likebookapp.util.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final MoodService moodService;
    private final UserService userService;
    private final LoggedUser loggedUser;


    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, MoodService moodService, UserService userService, LoggedUser loggedUser, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.moodService = moodService;
        this.userService = userService;
        this.loggedUser = loggedUser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addPost(PostServiceModel postServiceModel) {
        Post post = modelMapper.map(postServiceModel, Post.class);
        User user = userService.findById(loggedUser.getId());
        post.setMood(moodService.findByMoodName(postServiceModel.getMood()));
        post.setUser(user);
        this.postRepository.save(post);
    }

    @Override
    public List<PostViewModel> findAllByMood_MoodName(MoodsEnum moodsEnum) {
        return this.postRepository.findAllByMood_MoodName(moodsEnum)
                .stream()
                .map(posts -> modelMapper.map(posts, PostViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public int getNumberOfLikes() {
        return this.postRepository.findNumberOfLikes();
    }


    @Override
    public Set<Post> getPostsFromCurrentUser(Long id) {
        return this.postRepository.findPostByUserId(id).orElse(null);
    }


    public Set<PostsWithUsernamesServiceModel> getPostsFromOtherUsers(Long id) {
        Set<Post> postsByUserIdNot = this.postRepository.findPostByUserIdNot(id).orElse(null);

        return mapToPostWithUsernameDTO(postsByUserIdNot);
    }

    @Override
    public void removeById(Long id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public void likePostWithId(Long postId, Long userId) {
        Post post = this.postRepository.findById(postId).orElse(null);
        User user = userService.findUserById(userId).orElse(null);

        post.getUserLikes().add(user);
        post.setLikes(post.getLikes() + 1);
        this.postRepository.save(post);
    }


    private Set<PostsWithUsernamesServiceModel> mapToPostWithUsernameDTO(Set<Post> postsByUserIdNot) {
        return postsByUserIdNot.stream()
                .map(e -> {
                    PostsWithUsernamesServiceModel currentDTO = new PostsWithUsernamesServiceModel();
                    currentDTO.setContent(e.getContent());
                    currentDTO.setId(e.getId());
                    currentDTO.setLikes(e.getLikes());
                    currentDTO.setUserLikes(e.getUserLikes());
                    currentDTO.setMood(e.getMood().getMoodName());
                    currentDTO.setUsername(e.getUser().getUsername());
                    return currentDTO;
                })
                .collect(Collectors.toSet());
    }

}