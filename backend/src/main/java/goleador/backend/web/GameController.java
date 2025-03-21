package goleador.backend.web;

import goleador.backend.domain.game.service.GameService;
import goleador.backend.web.dto.GameData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/last-game")
    public ResponseEntity<GameData> getLastGame(@RequestParam String teamId) {
        UUID teamUuid = UUID.fromString(teamId);
        GameData lastGame = this.gameService.getLastGame(teamUuid);

    }
}
