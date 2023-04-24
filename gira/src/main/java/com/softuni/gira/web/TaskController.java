package com.softuni.gira.web;

import com.softuni.gira.entity.binding.TaskAddBindingModel;
import com.softuni.gira.entity.services.TaskServiceModel;
import com.softuni.gira.service.TaskService;
import com.softuni.gira.utils.LoggedUser;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final LoggedUser loggedUser;

    private final ModelMapper modelMapper;

    public TaskController(TaskService taskService, LoggedUser loggedUser, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.loggedUser = loggedUser;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    public String add(Model model){
        if (!model.containsAttribute("taskAddBindingModel")) {

            model.addAttribute("taskAddBindingModel", new TaskAddBindingModel());
            model.addAttribute("isAdded", false);
        }
        return "add-task";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid TaskAddBindingModel taskAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("taskAddBindingModel", taskAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskAddBindingModel",
                    bindingResult);
            return "redirect:add";
        }
        TaskServiceModel taskServiceModel = this.taskService.findByName(taskAddBindingModel.getName());

        if (taskServiceModel != null){
            redirectAttributes.addFlashAttribute("taskAddBindingModel", taskAddBindingModel);
            redirectAttributes.addFlashAttribute("isAdded", true);
            return "redirect:add";
        }

        taskService.addTask(modelMapper.map(taskAddBindingModel, TaskServiceModel.class));
        return "redirect:/";
    }

    @GetMapping("/progress/{id}")
    public String changeProgress(@PathVariable Long id){
        this.taskService.changeProgress(id);
        return "redirect:/";
    }

}
