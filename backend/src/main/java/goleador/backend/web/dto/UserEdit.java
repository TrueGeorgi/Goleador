package goleador.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEdit {
    private String firstName;
    private String lastName;
    private String email;
    private String profilePicture;
}
