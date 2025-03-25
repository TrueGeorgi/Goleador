package goleador.backend.domain.log.model;

import goleador.backend.domain.game.model.Game;
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
    private String message;

    @Column(nullable = false)
    private boolean isHomeGoal;

    @Column(nullable = false)
    private boolean isAwayGoal;

    @ManyToOne
    private Game game;
}
