package fast_food_rest.controller;

import fast_food_rest.entity.Basket;
import fast_food_rest.entity.User;
import fast_food_rest.payload.BasketDto;
import fast_food_rest.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
public class BasketController {


    @Autowired
    private BasketService basketService;

    @GetMapping
    public ResponseEntity<Basket> getUserBasket(@AuthenticationPrincipal User user) {
        Basket userBasket = basketService.getUserBasketItems(user);
        return ResponseEntity.ok(userBasket);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFoodToBasket(@RequestBody BasketDto basketDto, @AuthenticationPrincipal User user) {
        return basketService.addFoodToBasket(basketDto, user);
    }


    @GetMapping("/getQuantity/{foodId}")
    public ResponseEntity<Long> getQuantity(@PathVariable Long foodId, @AuthenticationPrincipal User user) {
        long quantity = basketService.getQuantity(foodId, user);
        return ResponseEntity.ok(quantity);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<String> removeFromBasket(@PathVariable Long foodId, @AuthenticationPrincipal User user) {
        return basketService.removeFromBasket(foodId, user);
    }

    @DeleteMapping("/checkout")
    public ResponseEntity<String> checkout(@AuthenticationPrincipal User user) {
        return basketService.checkout(user);
    }
}