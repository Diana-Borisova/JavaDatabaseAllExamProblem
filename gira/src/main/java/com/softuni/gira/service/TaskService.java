package com.softuni.gira.service;


import com.softuni.gira.entity.ProgressEnum;
import com.softuni.gira.entity.services.TaskServiceModel;
import com.softuni.gira.entity.view.TaskViewModel;

import java.util.List;

public interface TaskService {

    TaskServiceModel findByName(String name);

    void addTask(TaskServiceModel taskServiceModel);

   List<TaskViewModel> findAllByUserId(Long id);

   void changeProgress(Long id);

}
