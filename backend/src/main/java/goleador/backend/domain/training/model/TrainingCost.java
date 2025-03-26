package goleador.backend.domain.training.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training_cost")
public class TrainingCost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    String currentSkill;

    BigDecimal cost;
}
