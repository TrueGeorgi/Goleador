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
public class AttackingClub {
    private Club club;
    private String side;
}
