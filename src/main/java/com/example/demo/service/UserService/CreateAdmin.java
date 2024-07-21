package com.example.demo.service.UserService;

import com.example.demo.enums.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Configuration
@Slf4j
@Service
public class CreateAdmin {
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public CreateAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        runAtStart();
    }
    @PostConstruct
    public void runAtStart() {
        log.info("Creating admin");
        if(!userRepository.existsByEmail("AdminOne@gmail.com")) {
            User user = new User();
            user.setEmail("AdminOne@gmail.com");
            user.setPassword(passwordEncoder.encode("OneAdmin246"));
            user.setRole(Role.ADMIN);
            user.setCreationDate(LocalDateTime.now());
            user.setLastLogin(LocalDateTime.now());
            user.setPhoneNumber("09039156872");
            user.setFullName("David Black");
            user.setIsVerified(true);
            userRepository.save(user);
        }
    }
}
