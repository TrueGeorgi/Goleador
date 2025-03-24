package goleador.backend.web.mapper;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.log.model.Log;
import goleador.backend.web.dto.GameData;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameMapper {

    public GameData toGameData(Game game) {
        return GameData.builder()
                .hostTeamName(game.getHostTeam().getName())
                .hostTeamGoals(game.getGoalsTeamA())
                .awayTeamName(game.getAwayTeam().getName())
                .awayTeamGoals(game.getGoalsTeamB())
                .result(game.getResult())
                .logs(game.getLogs().stream().map(Log::getId).collect(Collectors.toList()))
                .build();
    }
}
