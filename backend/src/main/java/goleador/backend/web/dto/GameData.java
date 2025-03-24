package goleador.backend.web.dto;

import goleador.backend.domain.game.model.Result;
import goleador.backend.domain.log.model.Log;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

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
    private List<UUID> logs;
}
