package fast_food_rest.service;

import fast_food_rest.entity.Role;
import fast_food_rest.entity.User;
import fast_food_rest.repository.RoleRepository;
import fast_food_rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserRoles(Integer userId, Set<String> roleNames) {
        User user = userRepository.findById(userId).orElse(null);

        Set<Role> roles = roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName).orElse(null))
                .collect(Collectors.toSet());

        assert user != null;
        roles.addAll(user.getRoles());
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void removeRolesFromUser(Integer userId, List<String> roleNames) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Role> rolesToRemove = roleRepository.findByNameIn(roleNames);

        if (rolesToRemove.isEmpty()) {
            throw new RuntimeException("No matching roles found");
        }

        user.getRoles().removeAll(rolesToRemove);

        // Save the updated user
        userRepository.save(user);
    }
}
