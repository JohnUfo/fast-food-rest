package fast_food_rest.payload;

import fast_food_rest.entity.Category;
import fast_food_rest.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFoodDto {
    private Category category;
    private List<Food> foods;

}

