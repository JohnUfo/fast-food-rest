package fast_food_rest.service;


import fast_food_rest.entity.Attachment;
import fast_food_rest.entity.AttachmentContent;
import fast_food_rest.entity.Food;
import fast_food_rest.payload.FoodDto;
import fast_food_rest.repository.AttachmentContentRepository;
import fast_food_rest.repository.AttachmentRepository;
import fast_food_rest.repository.CategoryRepository;
import fast_food_rest.repository.FoodRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<String> createFood(MultipartFile file, FoodDto foodDto, Long categoryId) throws IOException {
        boolean existsByName = foodRepository.existsByName(foodDto.getName());
        if (existsByName) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Food is already exist");
        }

        Attachment attachment = null;

        if (file != null && !file.isEmpty()) {
            attachment = new Attachment();
            attachment.setFileOriginalName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            attachment.setName("uniqueFileName_" + System.currentTimeMillis());

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(attachment);
            attachment.setAttachmentContent(attachmentContent);
            attachment = attachmentRepository.save(attachment);
        }

        Food food = new Food();
        food.setName(foodDto.getName());
        food.setPrice(foodDto.getPrice());
        food.setDescription(foodDto.getDescription());
        food.setFile(attachment);
        food.setCategory(categoryRepository.findById(categoryId).orElseThrow());

        foodRepository.save(food);
        return ResponseEntity.status(HttpStatus.CREATED).body("Food added successfully");
    }

    public @NotNull ResponseEntity<Food> updateFood(Long foodId, MultipartFile file, FoodDto newFood) throws IOException {

        if (foodRepository.existsByNameAndIdNot(newFood.getName(), foodId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

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
