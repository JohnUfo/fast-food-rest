package fast_food_rest.service;

import fast_food_rest.entity.Category;
import fast_food_rest.payload.CategoryDto;
import fast_food_rest.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public @NotNull ResponseEntity<String> updateCategory(Long id, Category updatedCategory) {
        if (updatedCategory.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Give reliable name.");
        }

        if (categoryRepository.existsByNameAndIdNot(updatedCategory.getName(), id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists.");
        }

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found.");
        }

        Category category = optionalCategory.get();
        category.setName(updatedCategory.getName());
        categoryRepository.save(category);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category edited successfully.");
    }

    public @NotNull ResponseEntity<?> creteCategory(Category category) {
        if (category.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        try {
            boolean existsByName = categoryRepository.existsByName(category.getName());
            if (existsByName) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists.");
            }
            Category saved = categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while creating the category.");
        }
    }
}
