package fast_food_rest.loader;

import fast_food_rest.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class UserLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserLoader(final UserRepository userRepository,
                      final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(final ApplicationArguments args) {
//        if (userRepository.count() != 0) {
//            return;
//        }
//        final User user = new User();
//        user.setEmail("test@test.com");
//        user.setPassword(passwordEncoder.encode("123"));
//        userRepository.save(user);
    }

}

