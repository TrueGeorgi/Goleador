package goleador.backend.domain.game.service;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.service.ClubService;
import goleador.backend.domain.game.model.AttackingClub;
import goleador.backend.domain.game.model.Game;
import goleador.backend.domain.game.model.Goal;
import goleador.backend.domain.game.model.Result;
import goleador.backend.domain.game.repository.GameRepository;
import goleador.backend.domain.log.model.Log;
import goleador.backend.domain.log.service.LogService;
import goleador.backend.domain.player.model.Position;
import goleador.backend.domain.player.service.PlayerService;
import goleador.backend.web.dto.GameData;
import goleador.backend.web.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final ClubService clubService;
    private final LogService logService;
    private final PlayerService playerService;
    private final Random random;

    private int homeGkPower;
    private int homeDefPower;
    private int homeMidPower;
    private int homeAttPower;

    private int awayGkPower;
    private int awayDefPower;
    private int awayMidPower;
    private int awayAttPower;

    private int homeTeamStrength;
    private int awayTeamStrength;

    public Game playGame(UUID homeTeamUuid) {
        Club homeClub = clubService.getClubById(homeTeamUuid);
        Club awayClub = clubService.getRandomClub(homeTeamUuid);
        Game game = initializeGame(homeClub, awayClub);
        gameRepository.save(game);
        return game;
    }

    private Game initializeGame(Club homeClub, Club awayClub) {

        this.homeGkPower = playerService.calculatePower(homeClub.getId(), Position.GK);
        this.homeDefPower = playerService.calculatePower(homeClub.getId(), Position.DEF);
        this.homeMidPower = playerService.calculatePower(homeClub.getId(), Position.MID);
        this.homeAttPower = playerService.calculatePower(homeClub.getId(), Position.ATT);

        this.awayGkPower = playerService.calculatePower(awayClub.getId(), Position.GK);
        this.awayDefPower = playerService.calculatePower(awayClub.getId(), Position.DEF);
        this.awayMidPower = playerService.calculatePower(awayClub.getId(), Position.MID);
        this.awayAttPower = playerService.calculatePower(awayClub.getId(), Position.ATT);

        this.homeTeamStrength = this.homeGkPower + this.homeDefPower + this.homeMidPower + this.homeAttPower;
        this.awayTeamStrength = this.awayGkPower + this.awayDefPower + this.awayMidPower + this.awayAttPower;

        List<Log> gameLogs = new ArrayList<>();
        int homeGoals = 0;
        int awayGoals = 0;

        for (int i = 0; i <= 90 ; i++) {
            Goal goal = goalPossibility(homeClub, awayClub, homeGoals, awayGoals);
            if (goal.isGoal()) {
                switch (goal.getSide()) {
                    case "Home" -> homeGoals++;
                    case "Away" -> awayGoals++;
                }
            }

            Log log = logService.initializeLog(i, goal.getMessage());

            gameLogs.add(log);

        }

        Result gameResult;

        if (homeGoals > awayGoals) {
            gameResult = Result.TEAM_A;
        } else if (awayGoals > homeGoals) {
            gameResult = Result.TEAM_B;
        } else {
            gameResult =Result.DRAW;
        }

        return Game.builder()
                .date(LocalDate.now())
                .hostTeam(homeClub)
                .awayTeam(awayClub)
                .goalsTeamA(homeGoals)
                .goalsTeamB(awayGoals)
                .result(gameResult)
                .logs(gameLogs)
                .build();
    }

    private Goal goalPossibility(Club homeClub, Club awayClub, int homeGoals, int awayGoals) {

        AttackingClub controllingClub = calculateControllingClub(homeClub, awayClub);

        boolean isPastTheMid = isPassing(controllingClub.getSide());
        if (!isPastTheMid) {
            return Goal.builder()
                    .side(controllingClub.getSide())
                    .isGoal(false)
                    .message(controllingClub.getClub().getName() + " was attacking but it was stopped in midfield")
                    .build();
        }

        boolean isPastDef = isPassing(controllingClub.getSide());

        if (!isPastDef) {
            return Goal.builder()
                    .side(controllingClub.getSide())
                    .isGoal(false)
                    .message(controllingClub.getClub().getName() + " played well, but " + awayClub.getName() + "'s defence prevented the attack'")
                    .build();
        }

        boolean goal = isGoalScored(controllingClub.getSide());

        if (!goal) {
            return Goal.builder()
                    .side(controllingClub.getSide())
                    .isGoal(false)
                    .message(controllingClub.getClub().getName() + " made a shot, but the goalkeeper showed class")
                    .build();
        }

        switch (controllingClub.getSide()) {
            case "Home" -> homeGoals++;
            case "Away" -> awayGoals++;
        }

        return Goal.builder()
                .side(controllingClub.getSide())
                .isGoal(true)
                .message("GOOOOOOOAL!!! " + controllingClub.getClub().getName() + " Scored! The result is now " + homeGoals + " : " + awayGoals)
                .build();
    }

    private boolean isGoalScored(String controllingClub) {
        switch (controllingClub) {
            case "Home" -> {
                int total = homeAttPower + awayGkPower;
                int randomResult = random.nextInt(total + 1);

                return randomResult < homeAttPower;
            }
            case "Away" -> {
                int total = awayAttPower + homeGkPower;
                int randomResult = random.nextInt(total + 1);

                return randomResult < awayAttPower;
            }
            default -> throw new IllegalStateException("Unexpected value: " + controllingClub);
        }
    }

    private boolean isPassing(String controllingClub) {
        switch (controllingClub) {
            case "Home" -> {
                int attackPower = homeAttPower + (homeMidPower / 2);
                int defensePower = awayDefPower + (awayMidPower / 2);

                int total = attackPower + defensePower;
                int randomResult = random.nextInt(total + 1);

                return randomResult < attackPower;
            }
            case "Away" -> {
                int attackPower = awayAttPower + (awayMidPower / 2);
                int defensePower = homeDefPower + (homeMidPower / 2);

                int total = attackPower + defensePower;
                int randomResult = random.nextInt(total + 1);

                return randomResult < attackPower;
            }
            default -> throw new IllegalStateException("Unexpected value: " + controllingClub);
        }
    }

    private AttackingClub calculateControllingClub(Club homeClub, Club awayClub) {
        int totalPower = this.homeTeamStrength + this.awayTeamStrength;
        int result = random.nextInt(totalPower + 1);

        if (result < homeTeamStrength) {
            return AttackingClub.builder()
                    .club(homeClub)
                    .side("Home")
                    .build();
        } else {
            return AttackingClub.builder()
                    .club(awayClub)
                    .side("Away")
                    .build();
        }
    }

//    public GameData getLastGame(UUID teamId) {
//        Optional<Game> game = this.gameRepository.findFirstByHomeTeamIdOrAwayTeamIdOrderByCreatedOnDesc(teamId, teamId);
//        int test = 1;
//        if (game.isEmpty()) {
//            throw new RuntimeException("There is no last game"); // TODO - an error here
//        }
//
//        return gameMapper.toGameData(game.get());
//    }
}
