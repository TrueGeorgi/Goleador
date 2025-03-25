package goleador.backend.web;

import goleador.backend.domain.log.service.LogService;
import goleador.backend.web.dto.LogData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/log")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping("/game-logs")
    public ResponseEntity<List<LogData>> getGameLogs(@RequestParam String gameId) {
        UUID gameUuid = UUID.fromString(gameId);
        List<LogData> gameLogs = this.logService.getGameLogs(gameUuid);
        return ResponseEntity.ok(gameLogs);
    }
}
