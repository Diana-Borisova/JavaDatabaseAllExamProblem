package softuni.exam.instagraphlite.repository;


import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.instagraphlite.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String userName);

    @Query(value = "select u from User u order by u.posts.size desc , u.id")
    List<User> findAllUsersAndTheirPosts();

}
