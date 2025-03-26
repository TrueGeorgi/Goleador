package goleador.backend.domain.game.repository;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {

    Optional<Game> findFirstByHostTeamIdOrAwayTeamIdOrderByDateDesc(UUID homeTeamId, UUID awayTeamId);

    Optional<List<Game>> findAllByHostTeamIdOrAwayTeamIdOrderByDateDesc(UUID homeTeamId, UUID awayTeamId);
}
