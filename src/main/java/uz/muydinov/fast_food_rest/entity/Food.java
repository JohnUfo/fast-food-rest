package uz.muydinov.fast_food_rest.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.muydinov.fast_food_rest.entity.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Food extends AbsEntity {

    @OneToOne(fetch = FetchType.LAZY)
//    @Column(nullable = false) //each food needs one photo
    private Attachment foodPhoto;

    @Column(nullable = false, unique = true)
    private String name;

    private Double price;

    private String description;

    public Food(String name, Double price, String description, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)  // Enforcing non-nullable foreign key
    private Category category;
}
