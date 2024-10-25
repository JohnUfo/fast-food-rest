package fast_food_rest.entity;

import fast_food_rest.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasketItem extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    private Long quantity;
}
