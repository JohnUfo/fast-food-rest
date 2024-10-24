package fast_food_rest.service;


import fast_food_rest.entity.Role;
import fast_food_rest.payload.ApiResponse;
import fast_food_rest.payload.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import fast_food_rest.entity.User;
import fast_food_rest.repository.RoleRepository;
import fast_food_rest.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    JavaMailSender javaMailSender;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            return null;
        }
    }

    public ApiResponse registerUser(RegisterDto registerDto) {
        // Check if the user already exists
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ApiResponse("This user already exists", false);
        }

        // Create roles if they do not exist
        Role roleAdmin = roleRepository.findByName("ADMIN").orElseGet(() -> roleRepository.save(new Role("ADMIN")));
        Role roleFoodEditor = roleRepository.findByName("FOOD_EDITOR").orElseGet(() -> roleRepository.save(new Role("FOOD_EDITOR")));
        Role roleCategoryEditor = roleRepository.findByName("CATEGORY_EDITOR").orElseGet(() -> roleRepository.save(new Role("CATEGORY_EDITOR")));
        Role roleUser = roleRepository.findByName("USER").orElseGet(() -> roleRepository.save(new Role("USER")));

        // Create a new user
        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmailCode(String.valueOf(new Random().nextInt(999999)).substring(0, 4));

        // Assign roles based on email
        Set<Role> roles = new HashSet<>();
        if (user.getEmail().equals("m1lymoe16@gmail.com")) {
            roles.add(roleAdmin);
            roles.add(roleCategoryEditor);
            roles.add(roleFoodEditor);
        } else {
            roles.add(roleUser);  // Default role
        }
        user.setRoles(roles);
        userRepository.save(user);

        sendEmail(user.getEmail(), user.getEmailCode());

        return new ApiResponse("Verify your email!", true);
    }


    public void sendEmail(String sendingEmail, String emailCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("hvjjj83@gmail.com");
        mailMessage.setTo(sendingEmail);
        mailMessage.setSubject("Verify email");
        mailMessage.setText(emailCode);
        javaMailSender.send(mailMessage);
    }

    public boolean verifyEmail(String email, String code) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) return false;
        User user = optionalUser.get();
        if (user.getEmailCode().equals(code)) {
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
