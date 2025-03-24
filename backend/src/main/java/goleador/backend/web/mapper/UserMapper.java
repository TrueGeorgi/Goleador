package goleador.backend.web.mapper;

import goleador.backend.domain.user.model.User;
import goleador.backend.web.dto.UserData;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserData toUserData(User user) {
        return UserData.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .build();
    }

}
