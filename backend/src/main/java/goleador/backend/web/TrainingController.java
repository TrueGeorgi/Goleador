package goleador.backend.web;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.service.ClubService;
import goleador.backend.domain.player.service.PlayerService;
import goleador.backend.domain.training.client.dto.CreateTraining;
import goleador.backend.domain.training.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final ClubService clubService;
    private final PlayerService playerService;

    @PostMapping("/create-training")
    public ResponseEntity<Void> createTraining(@RequestBody CreateTraining createTraining) {
        trainingService.createTraining(createTraining);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/training-cost")
    public ResponseEntity<BigDecimal> getTrainingCost(@RequestParam int currentSkill) {
        BigDecimal trainingCost = trainingService.getTrainingCost(currentSkill);
        return ResponseEntity.ok(trainingCost);
    }

}
