package fast_food_rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Here you could implement any server-side logout logic if needed.
        // For JWT, typically you'd just clear the token on the client side.

        return ResponseEntity.ok("Logged out successfully");
    }
}
