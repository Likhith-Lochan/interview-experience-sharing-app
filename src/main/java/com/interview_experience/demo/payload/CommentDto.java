package com.interview_experience.demo.payload;

import com.interview_experience.demo.entities.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDto {

    private int id;

    private String content;


}
