package com.interview_experience.demo.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private  int Id;
    @NotEmpty
    @Size(min=4,message = "Username must be min 4 character !! ")
    private String name;
    @NotEmpty
    @Email(message = "Email address is not valid")
    private  String email;
    @NotEmpty
    @Size(min=3,max = 10,message = "password must be min of 3 characters and max of 10 ")
    private String password;
    @NotEmpty
    private int age;
    @NotEmpty
    private String gender;
}
