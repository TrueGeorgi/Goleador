package goleador.backend.web;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.game.service.GameService;
import goleador.backend.web.dto.GameData;
import goleador.backend.web.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @GetMapping("/last-game")
    public ResponseEntity<GameData> getLastGame(@RequestParam String teamId) {
        UUID teamUuid = UUID.fromString(teamId);
//        GameData lastGame = this.gameService.getLastGame(teamUuid);
        return null; // TODO - return should not be null
    }

    @PostMapping("play-game")
    public ResponseEntity<GameData> playGame(@RequestBody String homeClubId) {
        UUID homeTeamUuid = UUID.fromString(homeClubId);
        Game game = gameService.playGame(homeTeamUuid);
        GameData gameData = gameMapper.toGameData(game);
        int test1 = 1;
        return ResponseEntity.ok(gameData);
    }

    // TODO - get all games by user
}
