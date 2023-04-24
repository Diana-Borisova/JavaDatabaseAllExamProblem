package com.softuni.gira.service.impl;

import com.softuni.gira.entity.Classification;
import com.softuni.gira.entity.ProgressEnum;
import com.softuni.gira.entity.Task;
import com.softuni.gira.entity.User;
import com.softuni.gira.entity.services.TaskServiceModel;
import com.softuni.gira.entity.view.TaskViewModel;
import com.softuni.gira.repository.TaskRepository;
import com.softuni.gira.repository.UserRepository;
import com.softuni.gira.service.ClassificationService;
import com.softuni.gira.service.TaskService;
import com.softuni.gira.utils.LoggedUser;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final LoggedUser loggedUser;
    private final ClassificationService classificationService;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, LoggedUser loggedUser, ClassificationService classificationService, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.loggedUser = loggedUser;
        this.classificationService = classificationService;
        this.userRepository = userRepository;
    }

    @Override
    public TaskServiceModel findByName(String name) {
        return modelMapper.map(this.taskRepository.findByName(name), TaskServiceModel.class);
    }

    @Override
    public void addTask(TaskServiceModel taskServiceModel) {
            Task task = modelMapper.map(taskServiceModel, Task.class);
            User user = this.userRepository.findById(loggedUser.getId()).orElse(null);
        Classification classification = this.classificationService.findByClassificationName(taskServiceModel.getClassification());
            task.setClassification(classification);
            task.setDescription(taskServiceModel.getDescription());
            task.setUser(user);
            task.setName(taskServiceModel.getName());
            task.setDueDate(taskServiceModel.getDueDate());
            task.setProgressEnum(ProgressEnum.OPEN);
            this.taskRepository.save(task);
    }

    @Override
    public List<TaskViewModel> findAllByUserId(Long id) {
        return this.taskRepository.findAllByUserId(loggedUser.getId())
         .stream()
                .map(task -> modelMapper.map(task, TaskViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void changeProgress(Long id) {
        Task task = this.taskRepository.findById(id).orElse(null);
        ProgressEnum progressEnum = task.getProgressEnum();
        if (progressEnum == ProgressEnum.OPEN){
            task.setProgressEnum(ProgressEnum.IN_PROGRESS);
        } else if (progressEnum == ProgressEnum.IN_PROGRESS) {
            task.setProgressEnum(ProgressEnum.COMPLETED);
        } else if (progressEnum == ProgressEnum.COMPLETED) {
            this.taskRepository.deleteById(id);
            return;
        }
        this.taskRepository.save(task);
    }
}
