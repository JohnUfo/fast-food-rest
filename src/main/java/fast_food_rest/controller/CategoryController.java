package fast_food_rest.controller;

import fast_food_rest.entity.Category;
import fast_food_rest.entity.Food;
import fast_food_rest.payload.CategoryDto;
import fast_food_rest.payload.CategoryFoodDto;
import fast_food_rest.payload.FoodDto;
import fast_food_rest.repository.CategoryRepository;
import fast_food_rest.repository.FoodRepository;
import fast_food_rest.service.CategoryService;
import fast_food_rest.service.FoodService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FoodService foodService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return categoryService.creteCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok("Category deleted successfully.");
    }

    @GetMapping("/{categoryId}/foods")
    public ResponseEntity<?> getFoodsByCategory(@PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(null);
        return ResponseEntity.ok(new CategoryFoodDto(category, category.getFoods()));
    }

    @Transactional
    @PostMapping("/{categoryId}/foods")
    public ResponseEntity<String> addFood(@RequestParam("file") MultipartFile file, @ModelAttribute FoodDto foodDto, @PathVariable Long categoryId) throws IOException {
        return foodService.createFood(file, foodDto, categoryId);
    }

    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<String> deleteFood(@PathVariable Long foodId) {
        foodRepository.deleteById(foodId);
        return ResponseEntity.ok("Food deleted successfully.");
    }

    @PutMapping("/foods/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable Long foodId, @RequestParam(value = "file", required = false) MultipartFile file, @ModelAttribute FoodDto newFood) throws java.io.IOException {
        return foodService.updateFood(foodId, file, newFood);
    }
}