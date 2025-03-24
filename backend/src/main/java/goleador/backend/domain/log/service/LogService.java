package goleador.backend.domain.log.service;

import goleador.backend.domain.log.model.Log;
import goleador.backend.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public Log initializeLog(int minute, String message) {
        Log log = Log.builder()
                .minute(minute)
                .message(message)
                .build();

        logRepository.save(log);

        return log;

    }
}
