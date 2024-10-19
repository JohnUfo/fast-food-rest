package fast_food_rest.service;


import fast_food_rest.entity.Category;
import fast_food_rest.payload.ApiResponse;
import fast_food_rest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ApiResponse createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            return new ApiResponse("Category already exists", false);
        }
        categoryRepository.save(category);
        return new ApiResponse("Category created successfully", true);
    }

    public ApiResponse updateCategory(Integer id, Category newCategory) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(newCategory.getName());
            categoryRepository.save(category);
            return new ApiResponse("Category updated successfully", true);
        }).orElse(new ApiResponse("Category not found", false));
    }

    public ApiResponse deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            return new ApiResponse("Category not found", false);
        }
        categoryRepository.deleteById(id);
        return new ApiResponse("Category deleted successfully", true);
    }
}

