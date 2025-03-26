package goleador.backend.web.dto;

import goleador.backend.domain.player.model.Nationality;
import goleador.backend.domain.player.model.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerData {
    private String firstName;
    private String lastName;
    private Nationality nationality;
    private UUID id;
    private Position position;
    private int skill;
    private int goals;
    private int appearances;
}
