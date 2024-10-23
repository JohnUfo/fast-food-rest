package fast_food_rest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import fast_food_rest.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
