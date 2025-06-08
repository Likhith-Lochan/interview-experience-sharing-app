package com.interview_experience.demo.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {


    private  int categoryid;
    @NotBlank
    @Size(min=4,message = "Min size of category 4")
    private String categoryTitle;
    @NotBlank
    @Size(min=10 ,message = "min size of description is 10")
    private String categoryDescription;
}
