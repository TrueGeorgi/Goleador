package goleador.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingRequest {
    private String managerFirstName;
    private String managerLastName;
    private String clubName;
    private int points;
}
