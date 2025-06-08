package com.interview_experience.demo.serviceImpl;

import com.interview_experience.demo.Repo.UserRepo;
import com.interview_experience.demo.entities.User;
import com.interview_experience.demo.exception.ResourceNotFoundException;
import com.interview_experience.demo.payload.UserDto;
import com.interview_experience.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        User u=this.modelMapper.map(user,User.class);
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser=this.userRepo.save(u);

        return this.modelMapper.map(addedUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto user, Integer id) {

        User cat =this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","User Id",id));

        cat.setName(user.getName());
        cat.setAge(user.getAge());
        cat.setPassword(user.getPassword());
        cat.setEmail(user.getEmail());
        cat.setGender(user.getGender());

        User updatedUser=this.userRepo.save(cat);
        return this.modelMapper.map(updatedUser,UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User cat =this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","User Id",id));

        return this.modelMapper.map(cat,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> users=this.userRepo.findAll();

        List<UserDto> userdtos=users.stream().map((cat)->this.modelMapper.map(cat,UserDto.class)).toList();
        return userdtos;
    }

    @Override
    public void deleteUser(Integer id) {
        User cat =this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","User Id",id));
        this.userRepo.delete(cat);

    }
}
