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

import java.util.*;

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
            throw new UsernameNotFoundException(email);
        }
    }

    public ApiResponse registerUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ApiResponse("This user already exists", false);
        }


        List<Role> allRoles = roleRepository.findAll();

        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmailCode(String.valueOf(new Random().nextInt(999999)).substring(0, 4));

        Set<Role> roles = new HashSet<>();
        if (user.getEmail().equals("m1lymoe16@gmail.com")) {
            for (Role allRole : allRoles) {
                if (!allRole.getName().equals("USER")) {
                    roles.add(allRole);
                }
            }
        } else {
            for (Role allRole : allRoles) {
                if (allRole.getName().equals("USER")) {
                    roles.add(allRole);
                }
            }
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
