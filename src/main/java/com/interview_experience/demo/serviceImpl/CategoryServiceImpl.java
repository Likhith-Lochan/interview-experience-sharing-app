package com.interview_experience.demo.serviceImpl;

import com.interview_experience.demo.Repo.CategoryRepo;
import com.interview_experience.demo.entities.Category;
import com.interview_experience.demo.exception.ResourceNotFoundException;
import com.interview_experience.demo.payload.CategoryDto;
import com.interview_experience.demo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat=this.modelMapper.map(categoryDto,Category.class);

        Category addedCategory=this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCategory,CategoryDto.class);
    }

    @Override
    @CachePut(value = "categories",key = "#categoryId")
    public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
        Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID not found ",categoryId));
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        Category updatedCat=this.categoryRepo.save(cat);
        return this.modelMapper.map(updatedCat,CategoryDto.class);
    }

    @Override
    @CacheEvict(value = "categories",key = "#categoryId")
    public void deleteCategory(Integer categoryId) {
        Category cat =this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID not found ",categoryId));
        this.categoryRepo.delete(cat);

    }

    @Override
    @Cacheable(value = "categories",key = "#categoryId")
    public CategoryDto getCategoryById(Integer categoryId) {
        Category cat =this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID not found ",categoryId));

        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    @Cacheable(value = "categories")
    public List<CategoryDto> getCategories() {
        List<Category> categories=this.categoryRepo.findAll();
        List<CategoryDto> catdtos=categories.stream().map((cat)->this.modelMapper.map(cat,CategoryDto.class)).toList();
        return catdtos;
    }
}
