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

        Basket basket = user.getBasket();
        if (basket == null) {
            basket = new Basket();
            basket.setUser(user);
            user.setBasket(basket);
            basketRepository.save(basket);
            userRepository.save(user);
        }

        addOrUpdateBasketItem(basket, food, item.getQuantity());

        return ResponseEntity.ok("Food added to basket successfully");
    }

    private void addOrUpdateBasketItem(Basket basket, Food food, long quantity) {
        BasketItem basketItem = new BasketItem(basket, food, quantity);
        basket.getBasketItems().add(basketItem);
        basketRepository.save(basket);
    }

    public List<BasketItem> getBasketItems(User user) {
        Basket basketByUser = basketRepository.findBasketByUser(user);
        return basketItemRepository.findBasketItemByBasket(basketByUser);
    }
}
