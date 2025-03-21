package goleador.backend.web.dto;

import goleador.backend.domain.game.model.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameData {

    private String hostTeamName;
    private String awayTeamName;
    private int hostTeamGoals;
    private int awayTeamGoals;
    private Result result;
}
