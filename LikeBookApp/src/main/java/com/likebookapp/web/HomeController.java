package com.likebookapp.web;


import com.likebookapp.model.entity.MoodsEnum;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.model.service.PostsWithUsernamesServiceModel;
import com.likebookapp.model.views.PostViewModel;
import com.likebookapp.service.MoodService;
import com.likebookapp.service.PostService;
import com.likebookapp.service.UserService;
import com.likebookapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
public class HomeController {
    private final MoodService moodService;
    private final PostService postService;
    private final UserService userService;
    private final LoggedUser loggedUser;

    public HomeController(MoodService moodService, PostService postService, UserService userService, LoggedUser loggedUser) {
        this.moodService = moodService;
        this.postService = postService;
        this.userService = userService;
        this.loggedUser = loggedUser;

    }


    @GetMapping()
    public String index(Model model, HttpSession httpSession){
        if (httpSession.getAttribute("user") == null){
            return "index";
        }

        User user = userService.findUserById(loggedUser.getId()).orElse(null);

        model.addAttribute("currentUserInfo", user);
        Set<Post> postsFromCurrentUser = this.postService.getPostsFromCurrentUser(this.loggedUser.getId());
        model.addAttribute("userPosts", postsFromCurrentUser);
        Set<PostsWithUsernamesServiceModel> postsFromOtherUsers = this.postService.getPostsFromOtherUsers(this.loggedUser.getId());
        model.addAttribute("otherUserPosts", postsFromOtherUsers);
        model.addAttribute("user", user);

        return "home";
    }





}
