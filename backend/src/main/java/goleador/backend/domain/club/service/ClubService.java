package goleador.backend.domain.club.service;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.repository.ClubRepository;
import goleador.backend.domain.player.service.PlayerService;
import goleador.backend.domain.user.model.User;
import goleador.backend.web.dto.ClubEdit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final PlayerService playerService;
    private final Random random;

    public Club createClub(User user) {

        Club club = clubRepository.save(initializeClub(user));
        playerService.create15Players(club);

        return club;
    }

    private Club initializeClub(User user) {

        return Club.builder()
                 .name(user.getUsername() + "'s Club")
                 .createdOn(LocalDate.now())
                 .points(0)
                 .manager(user)
                 .finances(BigDecimal.valueOf(100000))
                 .build();
    }

    public Club getClubById(UUID id) {
        Optional<Club> club = clubRepository.findById(id);
        if (club.isEmpty()) {
            throw new RuntimeException("Club not found");
        }
        return club.get();
    }

    public Club getRandomClub(UUID homeClubId) {
        List<Club> clubs = clubRepository.findAll();
        if (clubs.size() <= 1) {
            throw new RuntimeException("Not enough clubs");
        }

        Club randomClub = clubs.get(random.nextInt(clubs.size()));

        while (randomClub.getId().equals(homeClubId)) {
            randomClub = clubs.get(random.nextInt(clubs.size()));
        }
        return randomClub;
    }

    public void editClub(UUID clubId, ClubEdit clubEdit) {
        Optional<Club> byId = clubRepository.findById(clubId);

        if (byId.isEmpty()) {
            throw new RuntimeException("Club not found");
        }

        Club club = byId.get();
        club.setName(clubEdit.getClubName());
        club.setLogo(clubEdit.getClubLogo());
        clubRepository.save(club);
    }

    public List<Club> getAllClubsByPointsDesc() {
       return clubRepository.findAllByOrderByPointsDesc();
    }

    public int getClubPosition(UUID uuid) {
        List<Club> clubs = getAllClubsByPointsDesc();

        for (int i = 0; i < clubs.size(); i++) {
            if (clubs.get(i).getId().equals(uuid)) {
                return i + 1;
            }
        }

        return -1;

    }

    public void deductPayment(BigDecimal price, UUID clubId) {
        Optional<Club> byId = clubRepository.findById(clubId);
        if (byId.isEmpty()) {
            throw new RuntimeException("Club not found");
        }
        Club club = byId.get();
        club.setFinances(club.getFinances().subtract(price));
        clubRepository.save(club);
    }

    public BigDecimal increaseFinances(BigDecimal amount, UUID clubId) {
        Optional<Club> byId = clubRepository.findById(clubId);
        if (byId.isEmpty()) {
            throw new RuntimeException("Club not found");
        }
        Club club = byId.get();
        club.setFinances(club.getFinances().add(amount));
        clubRepository.save(club);
        return club.getFinances();
    }

    public void setPoints(Club winner, Club looser) {
        winner.setPoints(winner.getPoints() + 3);
        int looserPoints = looser.getPoints();
        if (looserPoints < 3) {
            looser.setPoints(0);
        } else {
            looser.setPoints(looserPoints - 3);
        }
    }

    public void setDrawPoints(Club homeClub, Club awayClub) {
        homeClub.setPoints(homeClub.getPoints() + 1);
        awayClub.setPoints(awayClub.getPoints() + 1);
    }


}
