package goleador.backend.web.mapper;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.log.model.Log;
import goleador.backend.web.dto.GameData;
import goleador.backend.web.dto.GameDataWithCreatedOn;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameMapper {

    public GameData toGameData(Game game) {
        return GameData.builder()
                .homeTeamName(game.getHostTeam().getName())
                .homeTeamGoals(game.getGoalsTeamA())
                .awayTeamName(game.getAwayTeam().getName())
                .awayTeamGoals(game.getGoalsTeamB())
                .result(game.getResult())
                .gameId(game.getId())
                .build();
    }

    public GameDataWithCreatedOn toGameDataWithCreatedOn(Game game) {
        return GameDataWithCreatedOn.builder()
                .homeTeamName(game.getHostTeam().getName())
                .homeTeamGoals(game.getGoalsTeamA())
                .awayTeamName(game.getAwayTeam().getName())
                .awayTeamGoals(game.getGoalsTeamB())
                .gameId(game.getId())
                .createdOn(game.getDate())
                .build();
    }
}
