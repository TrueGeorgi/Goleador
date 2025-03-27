package goleador.training_svc.domain.training_cost.model;

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

    @Column(name = "skill_level", nullable = false)
    private int skillLevel;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private boolean active;

}
