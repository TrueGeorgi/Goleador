package goleador.backend.web.mapper;

import goleador.backend.domain.player.model.Player;
import goleador.backend.web.dto.PlayerData;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerData toPlayerData(Player player) {
        return PlayerData.builder()
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .nationality(player.getNationality())
                .clubId(player.getClub().getId())
                .position(player.getPosition())
                .skill(player.getSkill())
                .build();
    }
}
