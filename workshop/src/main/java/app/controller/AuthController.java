package app.controller;

import app.dto.AuthInfoRequest;
import app.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthInfoRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthInfoRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
