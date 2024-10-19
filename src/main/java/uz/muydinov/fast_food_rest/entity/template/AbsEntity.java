package uz.muydinov.fast_food_rest.entity.template;


import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
}
