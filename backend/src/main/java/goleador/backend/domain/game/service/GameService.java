package goleador.backend.domain.game.service;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.game.repository.GameRepository;
import goleador.backend.web.dto.GameData;
import goleador.backend.web.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

//    public GameData getLastGame(UUID teamId) {
//        Optional<Game> game = this.gameRepository.findFirstByHomeTeamIdOrAwayTeamIdOrderByCreatedOnDesc(teamId, teamId);
//        int test = 1;
//        if (game.isEmpty()) {
//            throw new RuntimeException("There is no last game"); // TODO - an error here
//        }
//
//        return gameMapper.toGameData(game.get());
//    }
}
