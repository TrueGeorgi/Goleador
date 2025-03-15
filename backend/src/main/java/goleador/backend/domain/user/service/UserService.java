package goleador.backend.domain.user.service;


import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.service.ClubService;
import goleador.backend.domain.user.model.User;
import goleador.backend.domain.user.model.UserRole;
import goleador.backend.domain.user.repository.UserRepository;
import goleador.backend.web.dto.LoginRequest;
import goleador.backend.web.dto.RegisterRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ClubService clubService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ClubService clubService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clubService = clubService;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public User register(RegisterRequest registerRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("Username is already in use");
        }

        User user = userRepository.save(initializeUser(registerRequest));

        Club club = clubService.createClub(user);
        user.setClub(club);

        return user;
    }

    private User initializeUser(RegisterRequest registerRequest) {

        return User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .build();
    }


    public User login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User with " + loginRequest.getUsername() + " not found");
        }

        User user = optionalUser.get();

        return user;
    }
}
