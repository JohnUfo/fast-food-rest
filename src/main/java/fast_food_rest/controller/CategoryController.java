package fast_food_rest.controller;

import fast_food_rest.entity.Category;
import fast_food_rest.entity.Food;
import fast_food_rest.payload.CategoryDto;
import fast_food_rest.payload.CategoryFoodDto;
import fast_food_rest.repository.CategoryRepository;
import fast_food_rest.repository.FoodRepository;
import fast_food_rest.service.CategoryService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService; // Assume you have a service layer
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    FoodRepository foodRepository;


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
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        if(category.getName().isBlank()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        try {
            Category savedCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory); // Return the created category in the response body
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while creating the category.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        if(category.getName().isBlank()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Give reliable name.");
        }
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

    @GetMapping("/{categoryId}/foods")
    public ResponseEntity<?> getFoodsByCategory(@PathVariable Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        List<Food> foods = foodRepository.findAllByCategoryId(categoryId);
        return ResponseEntity.ok(new CategoryFoodDto(category, foods));
    }

    @PostMapping("/{categoryId}/foods")
    public ResponseEntity<Food> addFoodToCategory(@PathVariable Integer categoryId, @RequestBody Food newFood) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow();

        Food food = new Food();
        food.setName(newFood.getName());
        food.setPrice(newFood.getPrice());
        food.setDescription(newFood.getDescription());
        food.setCategory(category);

        Food savedFood = foodRepository.save(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFood);
    }

    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<String> deleteFood(@PathVariable Integer foodId) {
        foodRepository.deleteById(foodId);
        return ResponseEntity.ok("Food deleted successfully.");
    }

    @PutMapping("/foods/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable Integer foodId, @RequestBody Food newFood) {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty())
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        Food food = optionalFood.get();
        food.setName(newFood.getName());
        food.setPrice(newFood.getPrice());
        food.setDescription(newFood.getDescription());
        Food savedFood = foodRepository.save(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFood);
    }
}