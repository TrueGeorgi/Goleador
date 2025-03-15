package goleador.backend.web;

import goleador.backend.domain.user.model.User;
import goleador.backend.domain.user.service.UserService;
import goleador.backend.web.dto.LoginRequest;
import goleador.backend.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public User login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }
}
