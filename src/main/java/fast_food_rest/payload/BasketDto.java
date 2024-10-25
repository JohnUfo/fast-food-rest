package fast_food_rest.payload;

import lombok.Data;

@Data
public class BasketDto {
    private Long foodId;
    private Long quantity;
}
