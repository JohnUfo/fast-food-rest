package fast_food_rest.repository;


import fast_food_rest.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fast_food_rest.entity.User;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<Set<Role>> findRolesByEmail(String email);
}
