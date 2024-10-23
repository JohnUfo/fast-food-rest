package fast_food_rest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import fast_food_rest.entity.Food;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    boolean existsByName(String name);

    List<Food> findAllByCategoryId(Long categoryId);
}
