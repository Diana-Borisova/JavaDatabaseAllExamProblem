package com.softuni.gira.web;

import com.softuni.gira.entity.binding.UserLoginBindingModel;
import com.softuni.gira.entity.binding.UserRegisterBindingModel;
import com.softuni.gira.entity.services.UserServiceModel;
import com.softuni.gira.service.UserService;
import com.softuni.gira.utils.LoggedUser;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

    private final LoggedUser loggedUser;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(LoggedUser loggedUser, UserService userService, ModelMapper modelMapper) {
        this.loggedUser = loggedUser;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public String register(Model model){
        if (!model.containsAttribute("userRegisterBindingModel")){
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

            return "register";

    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                    bindingResult);
            return "redirect:register";
        }
        UserServiceModel userServiceModel = this.userService.findUserByUsername(userRegisterBindingModel.getUsername());
        if (userServiceModel != null){
            return "redirect:register";
        }
        userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:login";

    }

    @GetMapping("/login")
    public String login(Model model){
        if(!model.containsAttribute("notFound")){
            model.addAttribute("notFound", false);
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
        }
        return "login";

    }

    @PostMapping("/login")
    public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel",
                    bindingResult);
            return "redirect:login";
        }
        UserServiceModel userServiceModel = this.userService.findByEmailAndPassword(userLoginBindingModel.getEmail(),
                userLoginBindingModel.getPassword());
        if (userServiceModel == null){
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("notFound", true);
            return "redirect:login";
        }
      //  userService.loginUser(modelMapper.map(userLoginBindingModel, UserServiceModel.class));

        loggedUser.setId(userServiceModel.getId());

        return "redirect:/";

    }

    @ModelAttribute
    public UserLoginBindingModel userLoginBindingModel(){
        return new UserLoginBindingModel();
    }
}
