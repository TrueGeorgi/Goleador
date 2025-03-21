package goleador.backend.domain.club.service;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.repository.ClubRepository;
import goleador.backend.domain.player.service.PlayerService;
import goleador.backend.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final PlayerService playerService;

    public Club createClub(User user) {

        Club club = clubRepository.save(initializeClub(user));
        playerService.create15Players(club);

        return club;
    }

    private Club initializeClub(User user) {
       Club club = Club.builder()
                .name(user.getUsername() + "'s Club")
                .createdOn(LocalDate.now())
                .points(0)
                .manager(user)
                .finances(BigDecimal.valueOf(100000))
                .build();


       return club;
    }

    public Club getClubById(UUID id) {
        return clubRepository.findById(id).orElse(null); // TODO handle error
    }
}
