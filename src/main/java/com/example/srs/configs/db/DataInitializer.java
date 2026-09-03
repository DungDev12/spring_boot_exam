package com.example.srs.configs.db;

import com.example.srs.commons.entities.Person;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.User;
import com.example.srs.repositories.RoleRepository;
import com.example.srs.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.count() > 0){
            return;
        }
        List<Role> roles = new ArrayList<>();
        if (roleRepository.count() == 0) {
            roles.add(new Role(null, "ADMIN", "System Administrator"));
            roles.add(new Role(null, "STUDENT", "System Student"));
            roles.add(new Role(null, "TEACHER", "System Teacher"));
            roleRepository.saveAll(roles);
        }
        System.out.println("========= Khởi tạo tài khoản ban đầu =========");
        Role adminRole = roleRepository
                .findByName("ADMIN").orElseThrow();

        String randomPassword = generateRandomPassword();

        User admin = User.builder()
                .username("admin")
                .email("admin@example.com")
                .passwordHash(passwordEncoder.encode(randomPassword))
                .person(Person.builder()
                        .lastName("Admin")
                        .build())
                .roles(Set.of(adminRole))
                .isActive(true)
                .build();

        userRepository.save(admin);
        System.out.println("""
                
                ======================================
                    TÀI KHOẢN KHỞI TẠO THÀNH CÔNG
                ======================================
                
                Username: admin
                Password: %s
                
                ======================================
                """.formatted(randomPassword));
    }

    private String generateRandomPassword(){
        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                        "abcdefghijklmnopqrstuvwxyz" +
                        "0123456789" +
                        "!@#$%^&*";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
}
