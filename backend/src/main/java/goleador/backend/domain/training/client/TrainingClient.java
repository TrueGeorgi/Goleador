package goleador.backend.domain.training.client;

import goleador.backend.domain.training.client.dto.CreateTraining;
import goleador.backend.domain.training.client.dto.TrainingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "training-svc", url = "http://localhost:8081/api/v1/training")
public interface TrainingClient {

    @PostMapping
    ResponseEntity<TrainingResponse> createTraining(@RequestBody CreateTraining createTraining);

    @GetMapping("/training-cost")
    ResponseEntity<BigDecimal> getTrainingCost(@RequestParam int currentSill);
}
