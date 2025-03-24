package goleador.backend.domain.player.repository;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.player.model.Player;
import goleador.backend.domain.player.model.Position;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends CrudRepository<Player, UUID> {

    Optional<List<Player>> findAllByClubId(UUID club_id);

    Optional<List<Player>> findAllByPosition(Position position);
}
