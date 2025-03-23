package goleador.backend.web;

import goleador.backend.domain.player.service.PlayerService;
import goleador.backend.web.dto.PlayerData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/teamsAllPlayers")
    public ResponseEntity<List<PlayerData>> getTeamsAllPlayers(@RequestParam String teamId) {
        UUID teamUuid = UUID.fromString(teamId);
        List<PlayerData> playerDataList = this.playerService.getTeamsPlayers(teamUuid);
        return ResponseEntity.ok(playerDataList);
    }

    // TODO - get top 5 goalscorers from a club
}
