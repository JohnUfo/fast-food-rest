package fast_food_rest.controller;

import fast_food_rest.entity.Food;
import fast_food_rest.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/{foodId}")
    public Food getFoodDetails(@PathVariable Long foodId) {
        return foodService.getFoodDetails(foodId);
    }
}
