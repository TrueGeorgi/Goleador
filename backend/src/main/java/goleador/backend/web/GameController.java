package goleador.backend.web;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.game.service.GameService;
import goleador.backend.web.dto.GameData;
import goleador.backend.web.dto.GameDataWithCreatedOn;
import goleador.backend.web.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @GetMapping("/last-game")
    public ResponseEntity<GameData> getLastGame(@RequestParam String teamId) {
        UUID teamUuid = UUID.fromString(teamId);
        Game lastGame = this.gameService.getLastGame(teamUuid);
        GameData gameData = gameMapper.toGameData(lastGame);
        return ResponseEntity.ok(gameData);
    }

    @PostMapping("play-game")
    public ResponseEntity<GameData> playGame(@RequestBody String homeClubId) {
        UUID homeTeamUuid = UUID.fromString(homeClubId);
        Game game = gameService.playGame(homeTeamUuid);
        GameData gameData = gameMapper.toGameData(game);
        return ResponseEntity.ok(gameData);
    }

    @GetMapping("/all-user-games-count")
    public ResponseEntity<BigDecimal> getAllUserGamesCount(@RequestParam String clubId) {
        UUID teamUuid = UUID.fromString(clubId);
        int gamesCount = gameService.getAllUserGamesCount(teamUuid);
        return ResponseEntity.ok(BigDecimal.valueOf(gamesCount));
    }

    @GetMapping("/all-user-games")
    public ResponseEntity<List<GameDataWithCreatedOn>> getAllUserGames(@RequestParam String clubId) {
        UUID teamUuid = UUID.fromString(clubId);
        List<Game> games = gameService.getAllUserGames(teamUuid);
        List<GameDataWithCreatedOn> gamesData = games.stream().map(gameMapper::toGameDataWithCreatedOn).toList();
        return ResponseEntity.ok(gamesData);
    }

}
