package goleador.backend.player.service;

import goleador.backend.club.model.Club;
import goleador.backend.player.model.*;
import goleador.backend.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    private final Random rand = new Random();

    public List<Player> create15Players(Club club) {

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            players.add(initializaPlayer(club));
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
}
