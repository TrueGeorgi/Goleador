package goleador.backend.web.dto;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private UUID clubId;
    private String profilePicture;
    private UserRole userRole;
}
