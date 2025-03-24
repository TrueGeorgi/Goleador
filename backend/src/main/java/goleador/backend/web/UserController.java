package goleador.backend.web;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.user.model.User;
import goleador.backend.domain.user.service.UserService;
import goleador.backend.web.dto.ClubData;
import goleador.backend.web.dto.UserData;
import goleador.backend.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // TODO - get all users ordered by points desc
    // TODO - edit user data
    // TODO - get user data

    @GetMapping("/{username}")
    public ResponseEntity<UserData> getUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        UserData userData = userMapper.toUserData(user);
        return ResponseEntity.ok(userData);
    }
}
