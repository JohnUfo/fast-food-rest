package fast_food_rest.controller;

import fast_food_rest.entity.Attachment;
import fast_food_rest.entity.AttachmentContent;
import fast_food_rest.entity.Category;
import fast_food_rest.entity.Food;
import fast_food_rest.payload.CategoryDto;
import fast_food_rest.payload.CategoryFoodDto;
import fast_food_rest.payload.FoodDto;
import fast_food_rest.repository.AttachmentContentRepository;
import fast_food_rest.repository.AttachmentRepository;
import fast_food_rest.repository.CategoryRepository;
import fast_food_rest.repository.FoodRepository;
import fast_food_rest.service.CategoryService;
import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;


    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        if (category.getName().isBlank()) {
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
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if (category.getName().isBlank()) {
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

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        for (Food food : optionalCategory.get().getFoods()) {

            if (food.getFile() != null) {
                Attachment attachment = food.getFile();
                attachmentContentRepository.deleteByAttachment(attachment);
                attachmentRepository.delete(attachment);
            }
            foodRepository.delete(food);

            foodRepository.deleteById(food.getId());
        }
        categoryRepository.deleteById(id);

        return ResponseEntity.ok("Category deleted successfully.");
    }

    @GetMapping("/{categoryId}/foods")
    public ResponseEntity<?> getFoodsByCategory(@PathVariable Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(null);

        return ResponseEntity.ok(new CategoryFoodDto(category, category.getFoods()));
    }

    @PostMapping("/{categoryId}/foods")
    public ResponseEntity<?> addFood(@RequestParam("file") MultipartFile file, @ModelAttribute FoodDto foodDto, @PathVariable Long categoryId) throws IOException, java.io.IOException {

        Attachment attachment = null;

        if (file != null && !file.isEmpty()) {
            attachment = new Attachment();
            attachment.setFileOriginalName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());

            // Generate a unique file name (you can use UUID, timestamp, etc.)
            attachment.setName("uniqueFileName_" + System.currentTimeMillis());

            // Save the Attachment entity first

            // Save the file bytes in AttachmentContent
            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(attachment);
            attachment.setAttachmentContent(attachmentContent);

            attachment = attachmentRepository.save(attachment);
        }

        // Step 2: Create and save the Food entity
        Food food = new Food();
        food.setName(foodDto.getName());
        food.setPrice(foodDto.getPrice());
        food.setDescription(foodDto.getDescription());
        food.setFile(attachment);
        food.setCategory(categoryRepository.findById(categoryId).orElseThrow());

        foodRepository.save(food);

        return ResponseEntity.status(HttpStatus.CREATED).body("Food added successfully");
    }

    @Transactional
    @DeleteMapping("/foods/{foodId}")
    public ResponseEntity<String> deleteFood(@PathVariable Long foodId) {
//        Food food = foodRepository.findById(foodId).orElseThrow();
//
//        if (food.getFile() != null) {
//            Attachment attachment = food.getFile();
//            attachmentContentRepository.deleteByAttachment(attachment);
//            attachmentRepository.delete(attachment);
//        }
//        foodRepository.delete(food);

        foodRepository.deleteById(foodId);
        return ResponseEntity.ok("Food deleted successfully.");
    }

    @PutMapping("/foods/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable Long foodId, @RequestParam("file") MultipartFile file, @ModelAttribute FoodDto newFood) throws java.io.IOException {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (optionalFood.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Food food = optionalFood.get();
        food.setName(newFood.getName());
        food.setPrice(newFood.getPrice());
        food.setDescription(newFood.getDescription());

        if (file != null && !file.isEmpty()) {

            Attachment attachment = food.getFile();
            attachment.setFileOriginalName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            attachment.setName("uniqueFileName_" + System.currentTimeMillis());

            attachment = attachmentRepository.save(attachment);

            Optional<AttachmentContent> attachmentContent = attachmentContentRepository.findByAttachmentId(attachment.getId());
            attachmentContent.get().setBytes(file.getBytes());
            attachmentContent.get().setAttachment(attachment);
            attachmentContentRepository.save(attachmentContent.get());
        }

        Food savedFood = foodRepository.save(food);
        return ResponseEntity.status(HttpStatus.OK).body(savedFood);
    }

}