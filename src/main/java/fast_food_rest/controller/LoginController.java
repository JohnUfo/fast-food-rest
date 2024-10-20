package fast_food_rest.controller;

import fast_food_rest.entity.Role;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<byte[]> getLoginPage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/login.html");
        byte[] content = Files.readAllBytes(Path.of(resource.getURI()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String email,
                                                     @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();
        try {

            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getEmail(), user);

            Set<Role> roles = user.getRoles();
            List<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toList());

            response.put("success", true);
            response.put("token", token);
            response.put("user", user);
            response.put("roles", roleNames);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Invalid email or password");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
