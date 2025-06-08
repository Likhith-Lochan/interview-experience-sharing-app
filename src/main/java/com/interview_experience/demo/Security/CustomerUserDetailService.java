package com.interview_experience.demo.Security;

import com.interview_experience.demo.Repo.UserRepo;
import com.interview_experience.demo.entities.User;
import com.interview_experience.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loading user from database by username

        User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email : "+username,0));
        return user;
    }
}
