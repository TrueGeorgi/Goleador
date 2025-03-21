package goleador.backend.domain.game.repository;

import goleador.backend.domain.game.model.Game;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository {

    Optional<Game> findFirstByHomeTeamIdOrAwayTeamIdOrderByCreatedOnDesc(UUID homeTeamId, UUID awayTeamId);
}
