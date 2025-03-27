package goleador.training_svc.web.mapper;

import goleador.training_svc.domain.training.model.Training;
import goleador.training_svc.web.dto.TrainingResponse;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public TrainingResponse toTrainingResponse(Training training) {
        return TrainingResponse.builder()
                .newSkillValue(training.getNewSkillLevel())
                .playerID(training.getPlayerId())
                .build();
    }
}
