package goleador.backend.web;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.user.model.User;
import goleador.backend.domain.user.model.UserRole;
import goleador.backend.domain.user.service.UserService;
import goleador.backend.web.dto.ClubData;
import goleador.backend.web.dto.UserData;
import goleador.backend.web.dto.UserEdit;
import goleador.backend.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{username}")
    public ResponseEntity<UserData> getUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        UserData userData = userMapper.toUserData(user);
        return ResponseEntity.ok(userData);
    }

    @GetMapping("/user-role/{username}")
    public ResponseEntity<UserRole> getUserRole(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user.getRole());
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUser(@PathVariable String username, @RequestBody UserEdit userEdit) {
        userService.editUser(username, userEdit);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().body(null);
    }
}
