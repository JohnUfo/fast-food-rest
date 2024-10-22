package fast_food_rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import fast_food_rest.entity.template.AbsEntity;

import javax.validation.constraints.Size;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category extends AbsEntity {

    @Column(unique = true, nullable = false)
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Food> foods;

    public List<Food> getFoods() {
        return foods == null ? List.of() : List.copyOf(foods); // Return immutable copy
    }
}
