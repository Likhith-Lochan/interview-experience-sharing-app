package com.interview_experience.demo.Repo;

import com.interview_experience.demo.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
