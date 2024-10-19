package fast_food_rest.controller;

import fast_food_rest.entity.User;
import fast_food_rest.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    // Serve the login.html file
    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<byte[]> getLoginPage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/login.html");
        byte[] content = Files.readAllBytes(Path.of(resource.getURI()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String email,
                                                     @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getEmail(), user); // Pass full name

            response.put("success", true);
            response.put("token", token);
            response.put("user", user); // Optionally send user info back
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    // API endpoint to return JSON data
    @GetMapping("/api/data")
    public ResponseEntity<Map<String, String>> getApiData() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "Hello from the API!");
        data.put("status", "success");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
