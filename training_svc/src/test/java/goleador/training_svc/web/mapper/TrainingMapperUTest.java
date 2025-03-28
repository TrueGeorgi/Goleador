package goleador.training_svc.web.mapper;

import goleador.training_svc.domain.training.model.Training;
import goleador.training_svc.web.dto.TrainingResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TrainingMapperUTest {

    private final TrainingMapper trainingMapper = new TrainingMapper();

    @Test
    void shouldMapTrainingToTrainingResponse() {
        // Given
        String playerId = "player-123";
        int newSkillLevel = 5;
        Training training = Training.builder()
                .playerId(playerId)
                .newSkillLevel(newSkillLevel)
                .build();

        // Expected response
        TrainingResponse expectedResponse = TrainingResponse.builder()
                .newSkillValue(newSkillLevel)
                .playerID(playerId)
                .build();

        // When
        TrainingResponse result = trainingMapper.toTrainingResponse(training);

        // Then
        assertEquals(expectedResponse.getNewSkillValue(), result.getNewSkillValue());
        assertEquals(expectedResponse.getPlayerID(), result.getPlayerID());
    }
}
