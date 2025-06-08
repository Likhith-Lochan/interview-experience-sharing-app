package com.interview_experience.demo.serviceImpl;

import com.interview_experience.demo.Repo.CommentRepo;
import com.interview_experience.demo.Repo.PostRepo;
import com.interview_experience.demo.entities.Comment;
import com.interview_experience.demo.entities.Post;
import com.interview_experience.demo.exception.ResourceNotFoundException;
import com.interview_experience.demo.payload.CommentDto;
import com.interview_experience.demo.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post_id",postId));
        Comment comment=this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment savedComment=this.commentRepo.save(comment);

        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","comment_id",commentId));
        this.commentRepo.delete(com);

    }
}
