package goleador.backend.user.service;


import goleador.backend.club.model.Club;
import goleador.backend.club.service.ClubService;
import goleador.backend.user.model.User;
import goleador.backend.user.model.UserRole;
import goleador.backend.user.repository.UserRepository;
import goleador.backend.web.dto.RegisterRequest;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ClubService clubService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ClubService clubService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clubService = clubService;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public User register(RegisterRequest registerRequest) {
        Optional<User> byUsername = userRepository.findByUsername(registerRequest.getUsername());

        if (byUsername.isPresent()) {
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



}
