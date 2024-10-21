package fast_food_rest.controller;

import fast_food_rest.entity.Category;
import fast_food_rest.entity.Food;
import fast_food_rest.payload.CategoryDto;
import fast_food_rest.repository.CategoryRepository;
import fast_food_rest.service.CategoryService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService; // Assume you have a service layer
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        try {
            boolean saved = categoryService.createCategory(category);
            if (saved) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the category.");
        }
    }


    @GetMapping("/{id}/foods")
    public ResponseEntity<List<Food>> getFoodsByCategoryId(@PathVariable Integer id) {
        List<Food> foods = categoryService.getFoodsByCategoryId(id);
        return ResponseEntity.ok(foods);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        try {
            boolean updated = categoryService.updateCategory(id, category);
            if (updated) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Category edited successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the category.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok("Category deleted successfully.");
    }

}
