package fast_food_rest.controller;

import fast_food_rest.entity.User;
import fast_food_rest.payload.ApiResponse;
import fast_food_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to get all users (only accessible by admin)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserRoles(@PathVariable Integer userId, @RequestBody Set<String> roles) {
        userService.updateUserRoles(userId, roles);
        return new ResponseEntity<>(new ApiResponse("User roles updated successfully", true), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/roles")
    public ResponseEntity<?> removeRoleFromUser(@PathVariable Integer userId, @RequestBody List<String> roles) {
        try {
            userService.removeRolesFromUser(userId, roles);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Role removed successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }

}
