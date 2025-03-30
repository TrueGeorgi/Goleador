package goleador.backend.domain.player.service;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.player.model.*;
import goleador.backend.domain.player.repository.PlayerRepository;
import goleador.backend.domain.training.client.dto.TrainingResponse;
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
    private final Random rand;

    public void create15Players(Club club) {
        for (int i = 0; i < 15; i++) {
            Player player = initializaPlayer(club);
            playerRepository.save(player);
        }
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

    public Nationality pickRandomNationality() {
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
                if (allByPosition.isEmpty()) {
                    throw new RuntimeException("No players found for position: GK");
                }
                return positionStrength(allByPosition);
            }
            case DEF -> {
                Optional<List<Player>> allByPosition = playerRepository.findAllByPosition(Position.DEF);
                if (allByPosition.isEmpty()) {
                    throw new RuntimeException("No players found for position: DEF");
                }
                return positionStrength(allByPosition);
            }
            case MID -> {
                Optional<List<Player>> allByPosition = playerRepository.findAllByPosition(Position.MID);
                if (allByPosition.isEmpty()) {
                    throw new RuntimeException("No players found for position: MID");
                }
                return positionStrength(allByPosition);
            }
            case ATT -> {
                Optional<List<Player>> allByPosition = playerRepository.findAllByPosition(Position.ATT);
                if (allByPosition.isEmpty()) {
                    throw new RuntimeException("No players found for position: ATT");
                }
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

    public void updatePlayerSkill(TrainingResponse trainingResponse) {
        UUID playerUuid = UUID.fromString(trainingResponse.getPlayerID());
        Optional<Player> playerById = playerRepository.findById(playerUuid);
        if (playerById.isEmpty()) {
            throw new RuntimeException(); // TODO - handle error
        }
        Player player = playerById.get();
        player.setSkill(trainingResponse.getNewSkillValue());
        playerRepository.save(player);

    }

    public Club getPlayersClub(UUID playerId) {
        Optional<Player> byId = playerRepository.findById(playerId);

        if (byId.isEmpty()) {
            throw new RuntimeException();
        }

        return byId.get().getClub();
    }

    public void selectGoalScorer(UUID id) {
        Optional<List<Player>> allByClubIdOrderByPosition = playerRepository.findAllByClubIdOrderByPosition(id);

        if (allByClubIdOrderByPosition.isEmpty()) {
            throw new RuntimeException();
        }

        List<Player> players = allByClubIdOrderByPosition.get();
        int playerPositionInList = rand.nextInt(players.size());
        Player player = players.get(playerPositionInList);
        player.setGoals(player.getGoals() + 1);
    }
}
