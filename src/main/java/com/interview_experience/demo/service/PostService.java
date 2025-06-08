package com.interview_experience.demo.service;

    import com.interview_experience.demo.payload.PostDto;
    import com.interview_experience.demo.payload.PostResponse;
    import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto updatedPost(PostDto postDto,Integer postId);

    void  deletePost(Integer postId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);


    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByCategory(Integer categoryId);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);
}
