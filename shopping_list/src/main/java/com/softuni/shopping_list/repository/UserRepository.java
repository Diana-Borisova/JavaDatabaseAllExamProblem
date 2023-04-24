package com.softuni.shopping_list.repository;

import com.softuni.shopping_list.model.entity.User;
import com.softuni.shopping_list.model.service.UserServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByUsername(String username);
      Optional<User> findByUsernameOrEmail(String username, String email);
      Optional<User> findByUsernameAndPassword(String username, String password);
}
