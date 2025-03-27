package goleador.training_svc.domain.training_cost.service;

import goleador.training_svc.domain.training_cost.model.TrainingCost;
import goleador.training_svc.domain.training_cost.repository.TrainingCostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingCostService implements CommandLineRunner {

    private final TrainingCostRepository trainingCostRepository;

    public BigDecimal getTrainingPrice(int skillLevel) {
        Optional<TrainingCost> firstBySkillLevelAndActiveTrue = trainingCostRepository.findFirstBySkillLevelAndActiveTrue(skillLevel);
        if (firstBySkillLevelAndActiveTrue.isEmpty()) {
            return BigDecimal.ZERO; // TODO - handle error
        }

        TrainingCost trainingCost = firstBySkillLevelAndActiveTrue.get();
        return trainingCost.getCost();
    }


    @Override
    public void run(String... args) {
        if (trainingCostRepository.count() == 0) {
            generateDefaultTrainingCosts();
        }
    }

    private void generateDefaultTrainingCosts() {
        int[] costs = {
                1000,
                5000,
                10000,
                20000,
                35000,
                55000,
                80000,
                110000,
                150000,
                200000,
                300000,
                450000,
                600000,
                800000,
                1000000,
                1300000,
                1700000,
                2300000,
                2900000,
                5000000
        };

        for (int i = 0; i < 20; i++) {
            TrainingCost trainingCost = TrainingCost.builder()
                    .skillLevel(i)
                    .cost(BigDecimal.valueOf(costs[i]))
                    .active(true)
                    .build();

            trainingCostRepository.save(trainingCost);
        }
    }
}
