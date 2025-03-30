package goleador.backend.web.dto;

import goleador.backend.domain.user.model.UserRole;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEdit {
    private String firstName;
    private String lastName;

    @Email
    private String email;

    @URL
    private String profilePicture;
    private UserRole role;
}
