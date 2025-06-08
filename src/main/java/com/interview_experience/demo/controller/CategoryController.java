package com.interview_experience.demo.controller;

import com.interview_experience.demo.payload.ApiResponse;
import com.interview_experience.demo.payload.CategoryDto;
import com.interview_experience.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory=this.categoryService.createCategory(categoryDto);

        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
        CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto,catId);

        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    public  ResponseEntity<ApiResponse> deleteCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);

        return new ResponseEntity<>(new ApiResponse("category is deleted successfully !!",true),HttpStatus.OK);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
        CategoryDto categoryDto=this.categoryService.getCategoryById(catId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categories=this.categoryService.getCategories();

        return ResponseEntity.ok(categories);
    }
}
