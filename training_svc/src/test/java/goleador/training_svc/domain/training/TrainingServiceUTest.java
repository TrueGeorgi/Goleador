package goleador.training_svc.domain.training;

import goleador.training_svc.domain.training.model.Training;
import goleador.training_svc.domain.training.repository.TrainingRepository;
import goleador.training_svc.domain.training.service.TrainingService;
import goleador.training_svc.domain.training_cost.service.TrainingCostService;
import goleador.training_svc.web.dto.CreateTraining;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceUTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingCostService trainingCostService;


    @InjectMocks
    private TrainingService trainingService;

    @Test
    void createTrainingSuccessfully() {

        // given
        UUID playerId = UUID.randomUUID();
        int oldSkill = 3;
        CreateTraining createTraining = new CreateTraining(oldSkill, playerId.toString());
        BigDecimal cost = BigDecimal.ONE;

        Training training = Training.builder()
                .oldSkillLevel(oldSkill)
                .newSkillLevel(4)
                .price(cost)
                .playerId(playerId.toString())
                .build();



        // when
        when(trainingCostService.getTrainingPrice(oldSkill)).thenReturn(cost);
        when(trainingRepository.save(any(Training.class))).thenReturn(training);

        // then
        Training result = trainingService.createTraining(createTraining);
        assertNotNull(result);
        assertEquals(3, result.getOldSkillLevel());
        assertEquals(4, result.getNewSkillLevel());
        assertEquals(cost, result.getPrice());
        assertEquals(playerId.toString(), result.getPlayerId());

        verify(trainingCostService, times(1)).getTrainingPrice(oldSkill);
        verify(trainingRepository, times(1)).save(any(Training.class));

    }

    @Test
    void shouldCallTrainingCostServiceWithCorrectSkillLevel() {
        // Given
        UUID playerId = UUID.randomUUID();
        int oldSkill = 3;
        CreateTraining createTraining = new CreateTraining(oldSkill, playerId.toString());
        BigDecimal cost = BigDecimal.ONE;
        when(trainingCostService.getTrainingPrice(3)).thenReturn(cost);

        // when
        trainingService.createTraining(createTraining);

        // then
        verify(trainingCostService, times(1)).getTrainingPrice(3);
    }

    @Test
    void shouldSaveTrainingObject() {
        // Given
        UUID playerId = UUID.randomUUID();
        int oldSkill = 3;
        CreateTraining createTraining = new CreateTraining(oldSkill, playerId.toString());
        BigDecimal cost = BigDecimal.ONE;

        // when
        when(trainingCostService.getTrainingPrice(oldSkill)).thenReturn(cost);
        trainingService.createTraining(createTraining);

        // then
        verify(trainingRepository, times(1)).save(any(Training.class));
    }

    @Test
    void shouldThrowExceptionIfRequestIsNull() {
        assertThrows(NullPointerException.class, () -> trainingService.createTraining(null));
    }

}
