package com.interview_experience.demo.Repo;

import com.interview_experience.demo.entities.Category;
import com.interview_experience.demo.entities.Post;
import com.interview_experience.demo.entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostRepo extends JpaRepository<Post,Integer> {
    @Cacheable(value = "users",key = "#user.id")
    List<Post> findByUser(User user);
    @Cacheable(value = "category",key = "#category.categoryid")
    List<Post> findByCategory(Category category);
    @Cacheable(value = "posts",key = "#title")
    List<Post> findByTitleContaining(String title);

}
