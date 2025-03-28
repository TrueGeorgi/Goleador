package goleador.training_svc.domain.training.service;

import goleador.training_svc.domain.training.model.Training;
import goleador.training_svc.domain.training.repository.TrainingRepository;
import goleador.training_svc.domain.training_cost.service.TrainingCostService;
import goleador.training_svc.web.dto.CreateTraining;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingCostService trainingCostService;

    public Training createTraining(CreateTraining createTraining) {

        BigDecimal cost = trainingCostService.getTrainingPrice(createTraining.getOldSkillLevel());

        Training training =  Training.builder()
                .oldSkillLevel(createTraining.getOldSkillLevel())
                .newSkillLevel(createTraining.getOldSkillLevel() + 1)
                .playerId(createTraining.getPlayerId())
                .price(cost)
                .build();

        trainingRepository.save(training);
        return training;
    }
}
