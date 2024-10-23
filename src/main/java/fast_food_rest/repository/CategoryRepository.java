package fast_food_rest.repository;


import fast_food_rest.payload.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import fast_food_rest.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

}
