package fast_food_rest.service;

import fast_food_rest.entity.Category;
import fast_food_rest.entity.Food;
import fast_food_rest.payload.CategoryDto;
import fast_food_rest.repository.CategoryRepository;
import fast_food_rest.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FoodRepository foodRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public Category createCategory(Category category) {
        boolean existsByName = categoryRepository.existsByName(category.getName());
        if (existsByName) {
            return null;
        }
        return categoryRepository.save(category);
    }

    public List<Food> getFoodsByCategoryId(Long categoryId) {
        return foodRepository.findAllByCategoryId(categoryId);
    }

    public boolean updateCategory(Long id, Category updatedCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return false;
        }
        Category category = optionalCategory.get();
        category.setName(updatedCategory.getName());
        categoryRepository.save(category);
        return true;
    }
}
