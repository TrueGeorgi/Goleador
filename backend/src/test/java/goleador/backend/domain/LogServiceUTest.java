package goleador.backend.domain;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.log.model.Log;
import goleador.backend.domain.log.repository.LogRepository;
import goleador.backend.domain.log.service.LogService;
import goleador.backend.web.dto.LogData;
import goleador.backend.web.mapper.LogMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogServiceUTest {

    @Mock
    LogRepository logRepository;
    @Mock
    LogMapper logMapper;

    @InjectMocks
    LogService logService;

    @Test
    void shouldInitializeLogSuccessfully() {

        int minute = 30;
        String message = "Goal scored by Home";
        boolean isHomeGoal = true;
        boolean isAwayGoal = false;

        UUID gameUuid = UUID.randomUUID();
        Game game = Game.builder()
                .id(gameUuid)
                .build();

        Log expectedLog = Log.builder()
                .minute(minute)
                .message(message)
                .isHomeGoal(isHomeGoal)
                .isAwayGoal(isAwayGoal)
                .game(game)
                .build();

        when(logRepository.save(Mockito.any(Log.class))).thenReturn(expectedLog);

        logService.initializeLog(minute, message, isHomeGoal, isAwayGoal, game);

        verify(logRepository, times(1)).save(Mockito.any(Log.class));

        verify(logRepository).save(argThat(log -> log.getMessage().equals(message)));
    }

    @Test
    void shouldGetGameLogsSuccessfully() {
        UUID gameUuid = UUID.randomUUID();
        Log log1 = Log.builder().minute(10).message("Goal scored by Home").isHomeGoal(true).isAwayGoal(false).game(new Game()).build();
        Log log2 = Log.builder().minute(20).message("Goal scored by Away").isHomeGoal(false).isAwayGoal(true).game(new Game()).build();
        List<Log> logs = List.of(log1, log2);

        LogData logData1 = new LogData(10, "Goal scored by Home", true, false);
        LogData logData2 = new LogData(20, "Goal scored by Away", false, true);

        when(logRepository.findAllByGameIdOrderByMinute(gameUuid)).thenReturn(Optional.of(logs));
        when(logMapper.toLogData(log1)).thenReturn(logData1);
        when(logMapper.toLogData(log2)).thenReturn(logData2);

        List<LogData> result = logService.getGameLogs(gameUuid);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(logData1.getMessage(), result.get(0).getMessage());
        assertEquals(logData2.getMessage(), result.get(1).getMessage());

        verify(logRepository, times(1)).findAllByGameIdOrderByMinute(gameUuid);
    }

    @Test
    void shouldThrowExceptionWhenNoLogsFound() {
        UUID gameUuid = UUID.randomUUID();

        when(logRepository.findAllByGameIdOrderByMinute(gameUuid)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> logService.getGameLogs(gameUuid));
        assertEquals("No logs found for game uuid: " + gameUuid, exception.getMessage());

        verify(logRepository, times(1)).findAllByGameIdOrderByMinute(gameUuid);
    }
}
