package goleador.backend.domain.club.model;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.player.model.Player;
import goleador.backend.domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, name = "created_on")
    private LocalDate createdOn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager_id")
    private User manager;

    private String logo;

    @OneToMany(mappedBy = "club", fetch = FetchType.EAGER)
    private List<Player> players;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hostTeam")
    private List<Game> homeGames;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "awayTeam")
    private List<Game> awayGames;

    @Column(nullable = false)
    private int points;

//    @Column(nullable = false, name = "eternal_position") // TODO - fix the eternal Ranking
//    private int eternalPosition;

    @Column(nullable = false)
    private BigDecimal finances;

//    private List<Transfer> transfers; // TODO - add list of transfers
}
