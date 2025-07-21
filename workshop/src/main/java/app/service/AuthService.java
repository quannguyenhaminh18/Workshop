package app.service;

import app.dto.AuthInfoRequest;
import app.entity.subclass.AuthInfo;
import app.entity.subclass.User;
import app.exception.BadRequestException;
import app.exception.ConflictException;
import app.exception.ForbiddenException;
import app.repository.AuthInfoRepository;
import app.util.JwtUtil;
import app.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthInfoRepository authInfoRepository;
    private final MessageUtil messageUtil;
    private final PasswordEncoder passwordEncoder;

    public boolean existedUsername(String username) {
        return authInfoRepository.existsByUsername(username);
    }

    public void register(AuthInfoRequest request) {
        if (existedUsername(request.getUsername())) {
            throw new ConflictException(messageUtil.get("username.exist"));
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        AuthInfo authInfo = new AuthInfo();
        authInfo.setUsername(request.getUsername());
        authInfo.setPassword(hashedPassword);
        authInfo.setActive(true);
        User user = new User();
        authInfo.setUser(user);
        user.setAuthInfo(authInfo);
        authInfoRepository.save(authInfo);
    }


    public String login(AuthInfoRequest request) {
        AuthInfo authInfo = authInfoRepository.findByUsername(request.getUsername());
        if (authInfo == null || !passwordEncoder.matches(request.getPassword(), authInfo.getPassword())) {
            throw new BadRequestException(messageUtil.get("auth.fail"));
        }
        if (!authInfo.isActive()) {
            throw new ForbiddenException(messageUtil.get("auth.locked"));
        }
        authInfo.setLastLogin(LocalDateTime.now());
        authInfoRepository.save(authInfo);
        return JwtUtil.generateToken(authInfo);
    }
}
