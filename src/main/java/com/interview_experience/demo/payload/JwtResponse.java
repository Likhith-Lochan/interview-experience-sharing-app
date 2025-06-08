package com.interview_experience.demo.payload;

import lombok.*;
import org.modelmapper.spi.Tokens;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtResponse {

    private String token;


    private String username;

}
