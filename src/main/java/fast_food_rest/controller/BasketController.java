package fast_food_rest.controller;

import fast_food_rest.entity.BasketItem;
import fast_food_rest.entity.User;
import fast_food_rest.payload.BasketDto;
import fast_food_rest.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
public class BasketController {


    @Autowired
    private BasketService basketService;

    @PostMapping("/add")
    public ResponseEntity<String> addFoodToBasket(
            @RequestBody BasketDto basketDto, @AuthenticationPrincipal User user) {
        return basketService.addFoodToBasket(basketDto,user);
    }

    @GetMapping
    public ResponseEntity<List<BasketItem>> getBasketItems(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getBasket().getBasketItems());
    }

    @GetMapping("/getQuantity/{foodId}")
    public ResponseEntity<Long> getQuantity(@PathVariable Long foodId, @AuthenticationPrincipal User user) {
        long quantity = basketService.getQuantity(foodId,user);
        return ResponseEntity.ok(quantity);
    }
}
