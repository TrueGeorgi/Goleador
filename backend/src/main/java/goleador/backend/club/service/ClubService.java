package goleador.backend.club.service;

import goleador.backend.club.model.Club;
import goleador.backend.club.repository.ClubRepository;
import goleador.backend.player.model.Player;
import goleador.backend.player.service.PlayerService;
import goleador.backend.user.model.User;
import goleador.backend.user.service.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final PlayerService playerService;

    public ClubService(ClubRepository clubRepository, UserService userService, PlayerService playerService) {
        this.clubRepository = clubRepository;
        this.playerService = playerService;
    }

    public Club createClub(User user) {
        Club club = initializeClub(user);

        clubRepository.save(club);
        playerService.create15Players(club);

        return club;
    }

    private Club initializeClub(User user) {
       return Club.builder()
                .name(user.getUsername() + "'s Club")
                .createdOn(LocalDate.now())
                .manager(user)
                .points(0)
                .finances(BigDecimal.valueOf(100000))
                .build();
    }
}
