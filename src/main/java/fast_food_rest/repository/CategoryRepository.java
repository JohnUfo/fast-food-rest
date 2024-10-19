package fast_food_rest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import fast_food_rest.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);

    Category getByName(String name);
}
