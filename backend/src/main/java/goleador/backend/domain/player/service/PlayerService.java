package goleador.backend.domain.player.service;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.player.model.*;
import goleador.backend.domain.player.repository.PlayerRepository;
import goleador.backend.web.dto.PlayerData;
import goleador.backend.web.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    private final Random rand = new Random();

    public List<Player> create15Players(Club club) {

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            Player player = initializaPlayer(club);
            playerRepository.save(player);
            players.add(player);
        }

        return players;
    }

    private Player initializaPlayer(Club club) {

        Nationality nationality = pickRandomNationality();
        String firstName = pickRandomFirstName(nationality);
        String lastName = pickRandomLastName(nationality);
        Position position = pickRandomPosition();

        return Player.builder()
                .firstName(firstName)
                .lastName(lastName)
                .nationality(nationality)
                .club(club)
                .position(position)
                .skill(rand.nextInt(15))
                .goals(0)
                .appearances(0)
                .build();
    }

    private Nationality pickRandomNationality() {
        int randomNum = rand.nextInt(Nationality.values().length);
        return Nationality.values()[randomNum];
    }

    private String pickRandomFirstName(Nationality nationality) {
        int randomNum;
        switch (nationality) {
            case BULGARIA -> {
                randomNum = rand.nextInt(BulgarianFirstName.values().length);
                return BulgarianFirstName.values()[randomNum].toString();
            }
            case BRAZIL -> {
                randomNum = rand.nextInt(BrazilianFirstName.values().length);
                return BrazilianFirstName.values()[randomNum].toString();
            }
            case GERMANY -> {
                randomNum = rand.nextInt(GermanFirstName.values().length);
                return GermanFirstName.values()[randomNum].toString();
            }
            case ENGLAND -> {
                randomNum = rand.nextInt(EnglishFirstName.values().length);
                return EnglishFirstName.values()[randomNum].toString();
            }
            case GREECE -> {
                randomNum = rand.nextInt(GreekFirstName.values().length);
                return GreekFirstName.values()[randomNum].toString();
            }
            default ->  throw new IllegalArgumentException();
        }
    }

    private String pickRandomLastName(Nationality nationality) {
        int randomNum;
        switch (nationality) {
            case BULGARIA -> {
                randomNum = rand.nextInt(BulgarianLastName.values().length);
                return BulgarianLastName.values()[randomNum].toString();
            }
            case BRAZIL -> {
                randomNum = rand.nextInt(BrazilianLastName.values().length);
                return BrazilianLastName.values()[randomNum].toString();
            }
            case GERMANY -> {
                randomNum = rand.nextInt(GermanLastName.values().length);
                return GermanLastName.values()[randomNum].toString();
            }
            case ENGLAND -> {
                randomNum = rand.nextInt(EnglishLastName.values().length);
                return EnglishLastName.values()[randomNum].toString();
            }
            case GREECE -> {
                randomNum = rand.nextInt(GreekLastName.values().length);
                return GreekLastName.values()[randomNum].toString();
            }
            default ->  throw new IllegalArgumentException();
        }
    }

    private Position pickRandomPosition() {
        int randomNum = rand.nextInt(Position.values().length);
        return Position.values()[randomNum];
    }

    public List<PlayerData> getTeamsPlayers(UUID teamId) {
        Optional<List<Player>> players = this.playerRepository.findAllByClubIdOrderByPosition(teamId);
        if (players.isEmpty()) {
            throw new RuntimeException(); // TODO - do something else instead of error
        }

        return players.get().stream().map(playerMapper::toPlayerData).collect(Collectors.toList());
    }

    public int calculatePower(UUID homeClubId, Position position) {
        switch (position) {
            case GK -> {
                Optional<List<Player>> allByPosition = playerRepository.findAllByPosition(Position.GK);
                return positionStrength(allByPosition);
            }
            case DEF -> {
                Optional<List<Player>> allByPosition = playerRepository.findAllByPosition(Position.DEF);
                return positionStrength(allByPosition);
            }
            case MID -> {
                Optional<List<Player>> allByPosition = playerRepository.findAllByPosition(Position.MID);
                return positionStrength(allByPosition);
            }
            case ATT -> {
                Optional<List<Player>> allByPosition = playerRepository.findAllByPosition(Position.ATT);
                return positionStrength(allByPosition);
            }
            default -> throw new IllegalArgumentException("Invalid position");
        }
    }

    private static int positionStrength(Optional<List<Player>> allByPosition) {
        if (allByPosition.isEmpty()) {
            throw new RuntimeException();
        }

        List<Player> players = allByPosition.get();

        return players.stream().mapToInt(Player::getSkill).sum();
    }
}
