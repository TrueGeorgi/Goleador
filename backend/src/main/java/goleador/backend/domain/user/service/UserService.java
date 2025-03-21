package goleador.backend.domain.user.service;


import goleador.backend.config.JwtService;
import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.service.ClubService;
import goleador.backend.domain.user.model.User;
import goleador.backend.domain.user.model.UserRole;
import goleador.backend.domain.user.repository.UserRepository;
import goleador.backend.web.dto.AuthenticationResponse;
import goleador.backend.web.dto.LoginRequest;
import goleador.backend.web.dto.RegisterRequest;
import goleador.backend.web.dto.UserData;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ClubService clubService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("Username is already in use");
        }

        User user = userRepository.save(initializeUser(registerRequest));

        Club club = clubService.createClub(user);
        user.setClub(club);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private User initializeUser(RegisterRequest registerRequest) {

        return User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .build();
    }


    public AuthenticationResponse login(LoginRequest loginRequest) {
//        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
//
//        if (optionalUser.isEmpty()) {
//            throw new RuntimeException("User with " + loginRequest.getUsername() + " not found");
//        }
//
//        User user = optionalUser.get();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        String jwtToken = jwtService.generateToken(user);

        UserData userdata = UserData.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .clubId(user.getClub().getId())
                .profilePicture(user.getProfilePicture())
                .build();

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userData(userdata)
                .build();
    }
}
