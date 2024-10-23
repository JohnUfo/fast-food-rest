package fast_food_rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import fast_food_rest.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString(exclude = "foods")
public class Category extends AbsEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Food> foods;

    public List<Food> getFoods() {
        return foods == null ? List.of() : List.copyOf(foods);
    }
}
