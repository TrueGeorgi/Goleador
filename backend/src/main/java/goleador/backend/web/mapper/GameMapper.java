package goleador.backend.web.mapper;

import goleador.backend.domain.game.model.Game;
import goleador.backend.web.dto.GameData;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameData toGameData(Game game) {
        return GameData.builder()
                .hostTeamName(game.getHostTeam().getName())
                .hostTeamGoals(game.getGoalsTeamA())
                .awayTeamName(game.getAwayTeam().getName())
                .awayTeamGoals(game.getGoalsTeamB())
                .result(game.getResult())
                .build();
    }
}
