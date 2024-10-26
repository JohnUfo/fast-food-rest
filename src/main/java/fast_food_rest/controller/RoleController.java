package fast_food_rest.controller;

import fast_food_rest.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/api/roles")
    public ResponseEntity<List<String>> getAllRoles() {
        List<String> roles = roleService.getRolesName();
        return ResponseEntity.ok(roles);
    }

}
