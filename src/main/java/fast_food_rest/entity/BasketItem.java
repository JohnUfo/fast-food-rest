package fast_food_rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import fast_food_rest.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(exclude = "basket")
public class BasketItem extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    @JsonIgnore
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    private Long quantity;
}
