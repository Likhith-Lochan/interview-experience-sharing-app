package com.interview_experience.demo.serviceImpl;

import com.interview_experience.demo.Repo.CategoryRepo;
import com.interview_experience.demo.Repo.PostRepo;
import com.interview_experience.demo.Repo.UserRepo;
import com.interview_experience.demo.entities.Category;
import com.interview_experience.demo.entities.Post;
import com.interview_experience.demo.entities.User;
import com.interview_experience.demo.exception.ResourceNotFoundException;
import com.interview_experience.demo.payload.PostDto;
import com.interview_experience.demo.payload.PostResponse;
import com.interview_experience.demo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","user id",userId));

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category id",categoryId));

        Post post =this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post newPost=this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatedPost(PostDto postDto,Integer postId) {
        Post post =this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post","postId",postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost=this.postRepo.save(post);


        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {

        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=sort.by(sortBy).ascending();
        }
        else{
            sort=sort.by(sortBy).descending();
        }
        Pageable pageable=PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost=this.postRepo.findAll(pageable);
        List<Post> allPosts=pagePost.getContent();
        List<PostDto> postDtos=allPosts.stream().map((p)->this.modelMapper.map(p,PostDto.class)).toList();

        PostResponse postResponse=new  PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post id ",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {

        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category id",categoryId));
        List<Post>  posts = this.postRepo.findByCategory(cat);
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","user id",userId));
        List<Post> posts= this.postRepo.findByUser(user);

        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).toList();

        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).toList();



        return postDtos;
    }
}
