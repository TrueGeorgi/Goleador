package goleador.backend.game.model;

import goleador.backend.club.model.Club;
import goleador.backend.log.model.Log;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "host_team_id", nullable = false)
    private Club hostTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private Club awayTeam;

    @Column(nullable = false, name = "goals_team_a")
    private int goalsTeamA;

    @Column(nullable = false, name = "goals_team_b")
    private int goalsTeamB;

    @Column(nullable = false)
    private Result result;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "game")
    private List<Log> logs;
}
