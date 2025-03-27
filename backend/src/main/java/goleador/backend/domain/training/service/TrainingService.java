package goleador.backend.domain.training.service;

import goleador.backend.domain.player.service.PlayerService;
import goleador.backend.domain.training.client.TrainingClient;
import goleador.backend.domain.training.client.dto.CreateTraining;
import goleador.backend.domain.training.client.dto.TrainingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingClient trainingClient;
    private final PlayerService playerService;

    public void createTraining(CreateTraining createTraining) {
        ResponseEntity<TrainingResponse> httpResponse = trainingClient.createTraining(createTraining);

        if (!httpResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Problem with creating new training");
        }

        TrainingResponse body = httpResponse.getBody();

        if (body != null) {
            playerService.updatePlayerSkill(body);
        }
    }

    public BigDecimal getTrainingCost(int currentSkill) {
        ResponseEntity<BigDecimal> httpResponse = trainingClient.getTrainingCost(currentSkill);

        if (!httpResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Problem with getting training cost");
        }

        return httpResponse.getBody();
    }
}
