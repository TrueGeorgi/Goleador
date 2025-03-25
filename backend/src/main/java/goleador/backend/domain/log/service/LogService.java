package goleador.backend.domain.log.service;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.log.model.Log;
import goleador.backend.domain.log.repository.LogRepository;
import goleador.backend.web.dto.LogData;
import goleador.backend.web.mapper.LogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final LogMapper logMapper;

    public void initializeLog(int minute, String message, boolean isHomeGoal, boolean isAwayGoal, Game game) {
        Log log = Log.builder()
                .minute(minute)
                .message(message)
                .isHomeGoal(isHomeGoal)
                .isAwayGoal(isAwayGoal)
                .game(game)
                .build();

        logRepository.save(log);

    }

    public List<LogData> getGameLogs(UUID gameUuid) {
        Optional<List<Log>> logs = this.logRepository.findAllByGameIdOrderByMinute(gameUuid);
        if (logs.isEmpty()) {
            throw  new RuntimeException("No logs found for game uuid: " + gameUuid); // TODO - handle errors
        }

        return logs.get().stream().map(logMapper::toLogData).collect(Collectors.toList());
    }
}
