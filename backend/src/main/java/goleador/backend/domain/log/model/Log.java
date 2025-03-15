package goleador.backend.domain.log.model;

import goleador.backend.domain.club.model.Game;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int minute;

    @Column(nullable = false)
    private boolean goal;

    @ManyToOne
    private Game game;

    @Column(nullable = false, name = "current_stand")
    private String currentStand;
}
