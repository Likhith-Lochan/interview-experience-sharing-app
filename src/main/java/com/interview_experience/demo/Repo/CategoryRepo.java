package com.interview_experience.demo.Repo;

import com.interview_experience.demo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
