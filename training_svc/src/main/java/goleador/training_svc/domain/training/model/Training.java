package goleador.training_svc.domain.training.model;

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
@Table(name = "training")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "old_skill_level",nullable = false)
    private int oldSkillLevel;

    @Column(name = "new_skill_level",nullable = false)
    private int newSkillLevel;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "player_id", nullable = false)
    private String playerId;
}
