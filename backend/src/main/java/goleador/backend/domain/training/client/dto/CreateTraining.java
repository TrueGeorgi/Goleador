package goleador.backend.domain.training.client.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTraining {

    @NotNull
    private int oldSkillLevel;

    @NotNull
    private String playerId;
}
