package goleador.backend.domain.user.service;


import goleador.backend.config.JwtService;
import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.service.ClubService;
import goleador.backend.domain.user.model.User;
import goleador.backend.domain.user.model.UserRole;
import goleador.backend.domain.user.repository.UserRepository;
import goleador.backend.web.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements CommandLineRunner {

    private final String DEFAULT_PROFILE_PICTURE = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSzy0oBDYLgLAY_B6JzM5RPbBOCnckZlW26bg&s";

    private final UserRepository userRepository;
    private final ClubService clubService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest, boolean isAdmin) {
        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());

        if (optionalUser.isPresent()) {
            throw new RuntimeException("Username is already in use");
        }

        User user = userRepository.save(initializeUser(registerRequest, isAdmin));

        Club club = clubService.createClub(user);
        user.setClub(club);

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        UserData userdata = UserData.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .clubId(user.getClub().getId())
                .profilePicture(DEFAULT_PROFILE_PICTURE)
                .build();


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userData(userdata)
                .build();
    }

    private User initializeUser(RegisterRequest registerRequest, boolean isAdmin) {

        UserRole role = UserRole.USER;

        if (isAdmin) {
            role = UserRole.ADMIN;
        }

        return User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();
    }

    protected void createFirstUser() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("Administrator")
                .password("123123")
                .build();

        register(registerRequest, true);
    }



    public AuthenticationResponse login(LoginRequest loginRequest) {
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

    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(); // TODO - handle error
    }

    public void editUser(String username, UserEdit userEdit) {
        User user = userRepository.findByUsername(username).orElseThrow(); // TODO - handle error
        user.setFirstName(userEdit.getFirstName());
        user.setLastName(userEdit.getLastName());
        user.setEmail(userEdit.getEmail());
        user.setProfilePicture(userEdit.getProfilePicture());
        userRepository.save(user);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            createFirstUser();
        }
    }
}
