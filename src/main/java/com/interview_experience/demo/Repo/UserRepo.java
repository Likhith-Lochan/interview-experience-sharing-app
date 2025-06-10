package com.interview_experience.demo.Repo;

import com.interview_experience.demo.entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
   @Cacheable(value = "users",key = "#email")
    public Optional<User> findByEmail(String email);

}
