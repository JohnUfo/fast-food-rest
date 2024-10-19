package fast_food_rest.service;


import fast_food_rest.entity.Food;
import fast_food_rest.payload.ApiResponse;
import fast_food_rest.repository.CategoryRepository;
import fast_food_rest.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse updateFood(Integer id, Food updatedFood) {
        boolean existsById = foodRepository.existsById(id);
        if (!existsById) {
            return new ApiResponse("Food is not exist", false);
        }
        Food food = foodRepository.findById(id).get();
        food.setName(updatedFood.getName());
        food.setDescription(updatedFood.getDescription());
        food.setPrice(updatedFood.getPrice());
        food.setCategory(updatedFood.getCategory());
        foodRepository.save(food);
        return new ApiResponse("Food updated successfully", true);
    }

    public boolean addFood(String foodName, double foodPrice, String foodDescription, Integer categoryId) {

        boolean exists = foodRepository.existsByName(foodName);
        if (exists) {
            return false;
        }
        foodRepository.save(new Food(
                foodName, foodPrice, foodDescription, categoryRepository.findById(categoryId).get()
        ));

        return true;
    }
}
