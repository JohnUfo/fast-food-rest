package fast_food_rest.service;


import fast_food_rest.entity.Basket;
import fast_food_rest.entity.BasketItem;
import fast_food_rest.entity.Food;
import fast_food_rest.entity.User;
import fast_food_rest.payload.BasketDto;
import fast_food_rest.repository.BasketItemRepository;
import fast_food_rest.repository.BasketRepository;
import fast_food_rest.repository.FoodRepository;
import fast_food_rest.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private BasketItemRepository basketItemRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public ResponseEntity<String> addFoodToBasket(BasketDto item, User user) {
        Food food = foodRepository.findById(item.getFoodId()).orElse(null);
        if (food == null) {
            return ResponseEntity.badRequest().body("Food not found");
        }

        List<BasketItem> basketItems = user.getBasket().getBasketItems();
        for (BasketItem basketItem : basketItems) {
            if (basketItem.getFood().getId().equals(item.getFoodId())) {
                basketItem.setQuantity(item.getQuantity());
                basketItemRepository.save(basketItem);
                return ResponseEntity.ok("Updated food quantity in basket");
            }
        }

        Basket basket = user.getBasket();
        BasketItem basketItem = new BasketItem(basket, food, item.getQuantity());
        basket.getBasketItems().add(basketItem);
        basketRepository.save(basket);
        return ResponseEntity.ok("Food added to basket successfully");
    }

    public int getQuantity(Long foodId, User user) {
        List<BasketItem> basketItems = user.getBasket().getBasketItems();
        for (BasketItem basketItem : basketItems) {
            if (basketItem.getFood().getId().equals(foodId)) {
                return Math.toIntExact(basketItem.getQuantity());
            }
        }
        return 0;
    }
}
