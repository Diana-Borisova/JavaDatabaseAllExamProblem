package com.softuni.jsonexercise.repositories;

import com.softuni.jsonexercise.domain.dto.users.UsersWithSoldProductsDto;
import com.softuni.jsonexercise.domain.entities.Category;
import com.softuni.jsonexercise.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "Select * from `product_shop`.users order by Rand() limit 1", nativeQuery = true)
    Optional<User> getRandomEntity();
    Optional<List<User>> findAllByOrderByLastNameAscFirstNameAsc();
}
