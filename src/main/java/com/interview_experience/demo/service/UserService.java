package com.interview_experience.demo.service;

import com.interview_experience.demo.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user,Integer id);

    UserDto getUserById(Integer id);

    List<UserDto> getAllUsers();

    void deleteUser(Integer id);
}
