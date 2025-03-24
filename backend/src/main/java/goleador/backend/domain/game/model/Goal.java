package goleador.backend.domain.game.model;

import goleador.backend.domain.club.model.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {
    private String side;
    private boolean isGoal;
    private String message;
}
