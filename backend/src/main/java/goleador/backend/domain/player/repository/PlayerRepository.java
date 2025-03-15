package goleador.backend.domain.player.repository;

import goleador.backend.domain.player.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<Player, UUID> {
}
