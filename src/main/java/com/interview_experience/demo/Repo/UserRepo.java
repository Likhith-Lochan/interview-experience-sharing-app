package com.interview_experience.demo.Repo;

import com.interview_experience.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    public Optional<User> findByEmail(String email);

}
