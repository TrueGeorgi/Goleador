package goleador.backend.domain;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.player.model.Nationality;
import goleador.backend.domain.player.model.Player;
import goleador.backend.domain.player.model.Position;
import goleador.backend.domain.player.repository.PlayerRepository;
import goleador.backend.domain.player.service.PlayerService;
import goleador.backend.domain.training.client.dto.TrainingResponse;
import goleador.backend.web.dto.PlayerData;
import goleador.backend.web.mapper.PlayerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceUTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @Mock
    private Random rand;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void shouldCreate15PlayersForClub() {
        Club club = new Club();
        Player playerMock = mock(Player.class);
        when(playerRepository.save(Mockito.any(Player.class))).thenReturn(playerMock);

        playerService.create15Players(club);

        verify(playerRepository, times(15)).save(Mockito.any(Player.class));
    }

    @Test
    void shouldPickRandomNationality() {

        when(rand.nextInt(Nationality.values().length)).thenReturn(0);

        Nationality nationality = playerService.pickRandomNationality();

        assertEquals(Nationality.BULGARIA, nationality);
    }

    @Test
    void shouldGetPlayersForTeam() {
        UUID clubId = UUID.randomUUID();
        Player player1 = new Player();
        Player player2 = new Player();
        List<Player> playerList = List.of(player1, player2);

        PlayerData playerData1 = new PlayerData();
        PlayerData playerData2 = new PlayerData();
        when(playerRepository.findAllByClubIdOrderByPosition(clubId)).thenReturn(Optional.of(playerList));
        when(playerMapper.toPlayerData(player1)).thenReturn(playerData1);
        when(playerMapper.toPlayerData(player2)).thenReturn(playerData2);

        List<PlayerData> players = playerService.getTeamsPlayers(clubId);

        assertNotNull(players);
        assertEquals(2, players.size());
        verify(playerRepository, times(1)).findAllByClubIdOrderByPosition(clubId);
    }

    @Test
    void shouldCalculatePowerForGoalkeeper() {
        UUID clubId = UUID.randomUUID();
        Player player1 = new Player();
        player1.setSkill(10);
        Player player2 = new Player();
        player2.setSkill(12);
        List<Player> gkPlayers = List.of(player1, player2);
        when(playerRepository.findAllByPosition(Position.GK)).thenReturn(Optional.of(gkPlayers));

        int power = playerService.calculatePower(clubId, Position.GK);

        assertEquals(22, power);
    }

    @Test
    void shouldUpdatePlayerSkill() {
        // Arrange
        String playerId = UUID.randomUUID().toString();
        int newSkill = 15;
        TrainingResponse trainingResponse = new TrainingResponse(playerId, newSkill);
        Player player = new Player();
        player.setSkill(10);
        when(playerRepository.findById(UUID.fromString(playerId))).thenReturn(Optional.of(player));
        when(playerRepository.save(player)).thenReturn(player);

        // Act
        playerService.updatePlayerSkill(trainingResponse);

        // Assert
        assertEquals(newSkill, player.getSkill());
        verify(playerRepository, times(1)).save(player);
    }


    @Test
    void shouldSelectGoalScorerForTeam() {
        UUID clubId = UUID.randomUUID();
        Player player1 = new Player();
        player1.setGoals(0);
        Player player2 = new Player();
        player2.setGoals(0);
        List<Player> players = List.of(player1, player2);
        when(playerRepository.findAllByClubIdOrderByPosition(clubId)).thenReturn(Optional.of(players));
        when(rand.nextInt(players.size())).thenReturn(0);

        playerService.selectGoalScorer(clubId);

        assertEquals(1, player1.getGoals());
    }

    @Test
    void shouldThrowExceptionWhenNoPlayersForPosition() {
        UUID clubId = UUID.randomUUID();

        when(playerRepository.findAllByPosition(Position.GK)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> playerService.calculatePower(clubId, Position.GK));
        assertEquals("No players found for position: GK", exception.getMessage());
    }
}
