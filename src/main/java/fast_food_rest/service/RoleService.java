package fast_food_rest.service;

import fast_food_rest.entity.Role;
import fast_food_rest.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<String> getRolesName() {
        List<String> rolesName = new ArrayList<>();
        for (Role role : roleRepository.findAll()) {
            rolesName.add(role.getName());
        }
        return rolesName;
    }
}
