package uz.muydinov.fast_food_rest.repository;

import fast_food_website.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
}
