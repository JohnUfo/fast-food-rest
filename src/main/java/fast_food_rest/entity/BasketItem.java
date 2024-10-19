package fast_food_rest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import fast_food_rest.entity.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasketItem extends AbsEntity {

    @ManyToOne
    private Food food;

    private int quantity;

    @ManyToOne
    private Basket basket;
}
