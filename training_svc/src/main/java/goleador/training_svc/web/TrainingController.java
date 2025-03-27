package goleador.training_svc.web;

import goleador.training_svc.domain.training.model.Training;
import goleador.training_svc.domain.training.service.TrainingService;
import goleador.training_svc.domain.training_cost.service.TrainingCostService;
import goleador.training_svc.web.dto.CreateTraining;
import goleador.training_svc.web.dto.TrainingResponse;
import goleador.training_svc.web.mapper.TrainingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingCostService trainingCostService;
    private final TrainingMapper trainingMapper;

    @PostMapping
    public ResponseEntity<TrainingResponse> createTraining(@RequestBody CreateTraining createTraining) {
        Training training = trainingService.createTraining(createTraining);
        TrainingResponse trainingResponse = trainingMapper.toTrainingResponse(training);
        return ResponseEntity.ok(trainingResponse);
    }

    @GetMapping("/training-cost")
    public ResponseEntity<BigDecimal> getTrainingCost(@RequestParam int currentSill) {
        BigDecimal trainingPrice = this.trainingCostService.getTrainingPrice(currentSill);
        return ResponseEntity.ok(trainingPrice);
    }

}
