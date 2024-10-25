package fast_food_rest.loader;

import fast_food_rest.entity.Category;
import fast_food_rest.entity.Food;
import fast_food_rest.entity.Role;
import fast_food_rest.repository.CategoryRepository;
import fast_food_rest.repository.FoodRepository;
import fast_food_rest.repository.RoleRepository;
import fast_food_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class UserLoader implements ApplicationRunner {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Override
    public void run(final ApplicationArguments args) {

        if (!"create".equalsIgnoreCase(ddlAuto)) {
            return;
        }

        // ROLE SAVE
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("FOOD_EDITOR"));
        roleRepository.save(new Role("CATEGORY_EDITOR"));
        roleRepository.save(new Role("USER"));

        // CATEGORY SAVE
        Category burgers = categoryRepository.save(new Category("Burgers", null));
        categoryRepository.save(new Category("Drinks", null));
        categoryRepository.save(new Category("Desserts", null));

//        Spicy Chicken Burger  - 8 USD
//        A crispy chicken patty topped with spicy mayo, lettuce, and pickles, served in a toasted bun.
//
//        Classic Cheeseburger - 9 USD
//        A juicy beef patty topped with melted cheddar cheese, lettuce, tomatoes, pickles, and our signature burger sauce, all sandwiched in a freshly toasted sesame bun.
//
//        Bacon BBQ Burger - 10 USD
//        A beef patty topped with sautéed mushrooms, melted Swiss cheese, caramelized onions, and creamy garlic aioli.
//
//        Spicy Jalapeño Burger - 10 USD
//        A zesty burger with pepper jack cheese, jalapeños, avocado slices, spicy mayo, and crispy lettuce on a toasted potato bun.
//
//        Double Decker Burger- 13 USD
//        Two beef patties stacked with American cheese, lettuce, tomatoes, pickles, and special sauce served on a fluffy toasted bun.
//
//        Chicken Avocado Burger - 10 USD
//        Grilled chicken breast, creamy avocado, lettuce, tomatoes, and chipotle mayo in a soft brioche bun.

    }
}
