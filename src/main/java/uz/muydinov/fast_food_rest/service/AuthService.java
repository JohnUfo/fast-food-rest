package uz.muydinov.fast_food_rest.service;

import fast_food_website.entity.Role;
import fast_food_website.entity.User;
import fast_food_website.payload.ApiResponse;
import fast_food_website.payload.RegisterDto;
import fast_food_website.repository.RoleRepository;
import fast_food_website.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

    public ApiResponse registerUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ApiResponse("This user already exists", false);
        }
        Role roleAdmin = roleRepository.save(new Role("ADMIN"));
        Role role_food_editor = roleRepository.save(new Role("FOOD_EDITOR"));
        Role role_category_editor = roleRepository.save(new Role("CATEGORY_EDITOR"));
        Role roleUser = roleRepository.save(new Role("USER"));

        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmailCode(String.valueOf(new Random().nextInt(999999)).substring(0, 4));
        Set<Role> roles = new HashSet<>();
        if (user.getEmail().equals("m1lymoe16@gmail.com")) {
            roles.add(roleAdmin);
            roles.add(role_category_editor);
            roles.add(role_food_editor);
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

    public ApiResponse verifyEmail(String verificationCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (verificationCode.equals(user.getEmailCode())) {
                user.setEnabled(true);
                user.setEmailCode(null);
                userRepository.save(user);
                return new ApiResponse("Account verified!", true);
            }
            return new ApiResponse("Invalid email code", false);
        }
        return new ApiResponse("User not found", false);
    }
}
