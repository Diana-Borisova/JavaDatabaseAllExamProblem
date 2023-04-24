package com.softuni.gira.repository;


import com.softuni.gira.entity.ProgressEnum;
import com.softuni.gira.entity.Task;
import com.softuni.gira.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findByName(String name);

    List<Task> findAllByUserId(Long id);

    boolean findByProgressEnum(ProgressEnum progressEnum);
}
