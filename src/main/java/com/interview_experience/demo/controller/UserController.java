package com.interview_experience.demo.controller;

import com.interview_experience.demo.entities.User;
import com.interview_experience.demo.payload.ApiResponse;
import com.interview_experience.demo.payload.UserDto;
import com.interview_experience.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userservice;

//    @PostMapping("/")
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto){
//        UserDto createdUserDto =this.userservice.createUser(userdto);
//
//        return new ResponseEntity<UserDto>(createdUserDto, HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto,@PathVariable Integer id){
        UserDto updatedUser=this.userservice.updateUser(userdto,id);

        return  ResponseEntity.ok(updatedUser);

    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers(){

        return ResponseEntity.ok(this.userservice.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getSingleUser(@Valid @PathVariable Integer id){
        return ResponseEntity.ok(this.userservice.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@Valid @PathVariable Integer id){
        this.userservice.deleteUser(id);

        return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
    }
}
