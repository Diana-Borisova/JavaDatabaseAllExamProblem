package com.likebookapp.repository;

import com.likebookapp.model.entity.MoodsEnum;
import com.likebookapp.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByMood(MoodsEnum moodsEnum);

    Optional<List<Post>> findAllByMood_MoodName(MoodsEnum moodsEnum);

    @Query("select count (p.userLikes) from  Post p ")
    int findNumberOfLikes();

    Optional<Set<Post>> findPostByUserIdNot(Long id);

    Optional<Set<Post>> findPostByUserId(Long id);

}
