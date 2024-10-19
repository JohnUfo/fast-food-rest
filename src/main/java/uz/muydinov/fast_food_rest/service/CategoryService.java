package uz.muydinov.fast_food_rest.service;

import fast_food_website.entity.Category;
import fast_food_website.payload.ApiResponse;
import fast_food_website.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse updateCategory(Integer id, String name) {
        boolean existsById = categoryRepository.existsById(id);

        if (!existsById) {
            return new ApiResponse("Category is not exist", false);
        }
        Category category = categoryRepository.findById(id).get();
        if(category.getName().equals(name)) {
            return new ApiResponse("Category name is same", false);
        }
        category.setName(name);
        categoryRepository.save(category);
        return new ApiResponse("Category updated successfully", true);
    }
}
