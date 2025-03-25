package goleador.backend.web.mapper;

import goleador.backend.domain.log.model.Log;
import goleador.backend.web.dto.LogData;
import org.springframework.stereotype.Component;

@Component
public class LogMapper {

    public LogData toLogData(Log log) {
        return LogData.builder()
                .message(log.getMessage())
                .minute(log.getMinute())
                .isHomeGoal(log.isHomeGoal())
                .isAwayGoal(log.isAwayGoal())
                .build();
    }
}
