package app.service;

import app.entity.subclass.AuthInfo;
import app.repository.AuthInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataInitService implements CommandLineRunner {
    private final AuthInfoRepository authInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (!authInfoRepository.existsByUsername(adminUsername)) {
            AuthInfo admin = new AuthInfo();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setActive(true);
            authInfoRepository.save(admin);
        }
    }
}

