package com.interview_experience.demo.controller;

import com.interview_experience.demo.payload.ApiResponse;
import com.interview_experience.demo.payload.CommentDto;
import com.interview_experience.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
        CommentDto createComment=this.commentService.createComment(commentDto,postId);

        return new ResponseEntity<>(createComment, HttpStatus.OK);

    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
            this.commentService.deleteComment(commentId);

            return new ResponseEntity<>(new ApiResponse("deleted comment successfully",true),HttpStatus.OK);
    }
}
