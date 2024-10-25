package fast_food_rest.repository;


import fast_food_rest.entity.Basket;
import fast_food_rest.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
    List<BasketItem> findBasketItemByBasket(Basket basket);
}
