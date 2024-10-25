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
    BasketRepository basketRepository;
    @Autowired
    BasketItemRepository basketItemRepository;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    public ResponseEntity<String> addFoodToBasket(BasketDto item) {
        Food food = foodRepository.findById(item.getFoodId()).orElse(null);
        if (food == null) {
            return ResponseEntity.badRequest().body("Food not found");
        }

        User user = userRepository.findUserByEmail(item.getEmail()).orElse(null);

        Basket basket = user.getBasket();
        if (basket == null) {
            basket = new Basket();
            basket.setUser(user);
            user.setBasket(basket);
        }

        BasketItem basketItem = new BasketItem();
        basketItem.setFood(food);
        basketItem.setQuantity(item.getQuantity());
        basketItem.setBasket(basket);

        basket.getBasketItems().add(basketItem);
        basketRepository.save(basket);
        return ResponseEntity.ok("Food added to basket successfully");
    }

    public List<BasketItem> getBasketItems(User user) {
        Basket basketByUser = basketRepository.findBasketByUser(user);
        return basketItemRepository.findBasketItemByBasket(basketByUser);
    }
}

