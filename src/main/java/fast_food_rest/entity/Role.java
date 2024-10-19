package fast_food_rest.entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import fast_food_rest.entity.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbsEntity implements GrantedAuthority {


    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
