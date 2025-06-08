package com.interview_experience.demo.service;

import com.interview_experience.demo.payload.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId);

    void deleteComment(Integer commentId);
}
