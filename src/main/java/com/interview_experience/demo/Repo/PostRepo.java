package com.interview_experience.demo.Repo;

import com.interview_experience.demo.entities.Category;
import com.interview_experience.demo.entities.Post;
import com.interview_experience.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);

}
