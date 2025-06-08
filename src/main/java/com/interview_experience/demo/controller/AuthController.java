package com.interview_experience.demo.controller;

import com.interview_experience.demo.payload.UserDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.interview_experience.demo.Security.JwtHelper;
import com.interview_experience.demo.payload.JwtRequest;
import com.interview_experience.demo.payload.JwtResponse;
import com.interview_experience.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private static final Logger logger= LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtHelper helper;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getUsername(),request.getPassword());

        UserDetails userDetails =userDetailsService.loadUserByUsername(request.getUsername());
        String token=this.helper.generateToken(userDetails);
        JwtResponse response=JwtResponse.builder()
                .token(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    private void doAuthenticate(String username ,String password){
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        try {
            authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid credentials ");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public  String exceptionHandler(){
        return "credentails Invalid !! ";
    }

    @PostMapping("/create-user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userdto){
        UserDto createdUserDto =this.userService.createUser(userdto);

        return new ResponseEntity<UserDto>(createdUserDto, HttpStatus.CREATED);
    }

}
