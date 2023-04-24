package com.likebookapp.web;

import com.likebookapp.model.binding.AddPostBindingModel;
import com.likebookapp.model.service.PostServiceModel;
import com.likebookapp.service.PostService;
import com.likebookapp.util.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final ModelMapper modelMapper;
    private final LoggedUser loggedUser;

    public PostController(PostService postService, ModelMapper modelMapper, LoggedUser loggedUser) {
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.loggedUser = loggedUser;
    }


    @GetMapping("/add")
    public String add(Model model){
        if (!model.containsAttribute(" addPostBindingModel")){
            model.addAttribute("productAddBindingModel", new AddPostBindingModel());
        }
        return "post-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid AddPostBindingModel addPostBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addPostBindingModel", addPostBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPostBindingModel",
                    bindingResult);
            return "redirect:add";
        }
        this.postService.addPost(modelMapper.map(addPostBindingModel, PostServiceModel.class));

        return "redirect:/";
    }

    @GetMapping("/remove/{id}")
    public String removeById(@PathVariable Long id){
        postService.removeById(id);
        return "redirect:/";
    }

    @GetMapping("/like-post/{id}")
    public String likePostWithId(@PathVariable Long id){
        postService.likePostWithId(id, loggedUser.getId());
        return "redirect:/";
    }

    @ModelAttribute
    public AddPostBindingModel addPostBindingModel (){
        return  new AddPostBindingModel();
}}
