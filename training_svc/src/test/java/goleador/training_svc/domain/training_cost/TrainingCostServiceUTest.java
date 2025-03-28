package goleador.training_svc.domain.training_cost;

import goleador.training_svc.domain.training_cost.model.TrainingCost;
import goleador.training_svc.domain.training_cost.repository.TrainingCostRepository;
import goleador.training_svc.domain.training_cost.service.TrainingCostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingCostServiceUTest {

    @Mock
    private TrainingCostRepository trainingCostRepository;

    @InjectMocks
    private TrainingCostService trainingCostService;

    @Test
    void shouldReturnTrainingPrice_WhenTrainingCostExists() {
        // Given
        int skillLevel = 3;
        BigDecimal expectedCost = BigDecimal.valueOf(20000);
        TrainingCost trainingCost = TrainingCost.builder()
                .skillLevel(skillLevel)
                .cost(expectedCost)
                .active(true)
                .build();

        when(trainingCostRepository.findFirstBySkillLevelAndActiveTrue(skillLevel))
                .thenReturn(Optional.of(trainingCost));

        // When
        BigDecimal result = trainingCostService.getTrainingPrice(skillLevel);

        // Then
        assertEquals(expectedCost, result);
        verify(trainingCostRepository, times(1)).findFirstBySkillLevelAndActiveTrue(skillLevel);
    }

    @Test
    void shouldReturnZero_WhenNoTrainingCostExists() {
        // Given
        int skillLevel = 5;
        when(trainingCostRepository.findFirstBySkillLevelAndActiveTrue(skillLevel))
                .thenReturn(Optional.empty());

        // When
        BigDecimal result = trainingCostService.getTrainingPrice(skillLevel);

        // Then
        assertEquals(BigDecimal.ZERO, result);
        verify(trainingCostRepository, times(1)).findFirstBySkillLevelAndActiveTrue(skillLevel);
    }

    @Test
    void shouldGenerateDefaultTrainingCosts_WhenRepositoryIsEmpty() {
        // Given
        when(trainingCostRepository.count()).thenReturn(0L);

        // When
        trainingCostService.run();

        // Then
        verify(trainingCostRepository, times(20)).save(any(TrainingCost.class));
    }

    @Test
    void shouldNotGenerateDefaultTrainingCosts_WhenRepositoryIsNotEmpty() {
        // Given
        when(trainingCostRepository.count()).thenReturn(5L);

        // When
        trainingCostService.run();

        // Then
        verify(trainingCostRepository, never()).save(any(TrainingCost.class));
    }
}
