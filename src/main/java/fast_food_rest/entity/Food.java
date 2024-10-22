package fast_food_rest.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import fast_food_rest.entity.template.AbsEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Food extends AbsEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Attachment foodPhoto;

    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 100, message = "Food name must be between 2 and 100 characters")
    private String name;

    @Column(nullable = false)
    @Min(value = 0, message = "Price must be a positive value")
    private Double price;

    @Column(length = 1000)
    @Size(max = 1000, message = "Description cannot be longer than 1000 characters")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Constructor without foodPhoto
    public Food(String name, Double price, String description, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }
}
