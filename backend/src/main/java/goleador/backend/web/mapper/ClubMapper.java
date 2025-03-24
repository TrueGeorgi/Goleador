package goleador.backend.web.mapper;


import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.player.model.Player;
import goleador.backend.web.dto.ClubData;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ClubMapper {

    public ClubData toClubData(Club club) {
        return ClubData.builder()
                .clubName(club.getName())
                .createdOn(club.getCreatedOn())
                .managerId(club.getManager().getId())
                .logo(club.getLogo())
                .playersIds(club.getPlayers().stream().map(Player::getId).collect(Collectors.toList()))
                .homeGamesIds(club.getHomeGames().stream().map(Game::getId).collect(Collectors.toList()))
                .awayGamesIds(club.getAwayGames().stream().map(Game::getId).collect(Collectors.toList()))
                .points(club.getPoints())
                .finances(club.getFinances())
                .build();
    }
}
