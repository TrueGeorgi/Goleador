package goleador.backend.domain.log.repository;

import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.log.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<Log, UUID> {
    Optional<List<Log>> findAllByGameIdOrderByMinute(UUID gameId);
}
