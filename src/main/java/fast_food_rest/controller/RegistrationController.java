package fast_food_rest.controller;

import fast_food_rest.payload.ApiResponse;
import fast_food_rest.payload.RegisterDto;
import fast_food_rest.service.AuthService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class RegistrationController {

    @Autowired
    private AuthService authService; // Assuming you have a service for user operations

    @GetMapping(value = "/register", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<byte[]> getRegisterPage() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/register.html");
        byte[] content = Files.readAllBytes(Path.of(resource.getURI()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<>(content, headers, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterDto registerDto) {
        ApiResponse response = authService.registerUser(registerDto);
        return new ResponseEntity<>(response, response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponse> verifyEmail(@RequestParam String email, @RequestParam String code) {
        boolean isVerified = authService.verifyEmail(email, code);
        return new ResponseEntity<>(new ApiResponse(isVerified ? "Email verified!" : "Invalid code!", isVerified),
                isVerified ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
