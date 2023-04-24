package com.softuni.gira.web;

import com.softuni.gira.entity.view.TaskViewModel;
import com.softuni.gira.service.ClassificationService;
import com.softuni.gira.service.TaskService;
import com.softuni.gira.utils.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {
    private final LoggedUser loggedUser;
    private final TaskService taskService;

    private final ClassificationService classificationService;

    public HomeController(LoggedUser loggedUser, TaskService taskService, ClassificationService classificationService) {
        this.loggedUser = loggedUser;
        this.taskService = taskService;
        this.classificationService = classificationService;
    }


    @GetMapping("/")
    public String index(Model model){
        if (loggedUser.getId() == null){
            return "index";
        }
        List<TaskViewModel> tasks = taskService.findAllByUserId(loggedUser.getId());
        model.addAttribute("tasks", tasks);

        return "home";
    }

    @ModelAttribute
    public TaskViewModel taskViewModel(){
        return new TaskViewModel();
    }
}
