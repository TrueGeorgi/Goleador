package goleador.backend.domain;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.repository.ClubRepository;
import goleador.backend.domain.club.service.ClubService;
import goleador.backend.domain.player.service.PlayerService;
import goleador.backend.domain.user.model.User;
import goleador.backend.domain.user.model.UserRole;
import goleador.backend.web.dto.ClubEdit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClubServiceUTest {
    @Mock
    private ClubRepository clubRepository;
    @Mock
    private PlayerService playerService;
    @Mock
    private Random random;

    @InjectMocks
    private ClubService clubService;


    @Test
    void happyPathForCreateClub() {

        UUID managerId = UUID.randomUUID();

        User user = User.builder()
                .role(UserRole.user)
                .id(managerId)
                .email("test@gmail.com")
                .password("password")
                .username("test")
                .isDeleted(false)
                .build();

        Club mockClub = Club.builder()
                .id(UUID.randomUUID())
                .name("test's Club")
                .createdOn(LocalDate.now())
                .points(0)
                .manager(user)
                .finances(BigDecimal.valueOf(100000))
                .build();

        when(clubRepository.save(any(Club.class))).thenReturn(mockClub);
        Club result = clubService.createClub(user);
        assertEquals(mockClub.getName(), result.getName());
        assertEquals(user, result.getManager());
        assertEquals(0, result.getPoints());
        assertEquals(BigDecimal.valueOf(100000), result.getFinances());

        verify(playerService, times(1)).create15Players(result);
    }

    @Test
    void shouldReturnClubWhenFound() {
        // Given
        UUID uuid = UUID.randomUUID();
        Club club = Club.builder()
                .id(uuid)
                .name("Test Club")
                .createdOn(LocalDate.now())
                .points(10)
                .build();

        when(clubRepository.findById(uuid)).thenReturn(Optional.of(club));

        // When
        Club result = clubService.getClubById(uuid);

        // Then
        assertNotNull(result);
        assertEquals(uuid, result.getId());
        assertEquals("Test Club", result.getName());
        verify(clubRepository, times(1)).findById(uuid);
    }

    @Test
    void shouldThrowExceptionWhenClubNotFound() {
        // Given
        UUID uuid = UUID.randomUUID();
        when(clubRepository.findById(uuid)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clubService.getClubById(uuid));
        assertEquals("Club not found", exception.getMessage());

        verify(clubRepository, times(1)).findById(uuid);
    }

    @Test
    void shouldReturnRandomClubDifferentFromHomeClub() {
        // Given
        UUID homeClubId = UUID.randomUUID();
        UUID awayClubId = UUID.randomUUID();
        UUID anotherClubId = UUID.randomUUID();

        Club homeClub = Club.builder().id(homeClubId).name("Home Club").build();
        Club awayClub = Club.builder().id(awayClubId).name("Away Club").build();
        Club anotherClub = Club.builder().id(anotherClubId).name("Another Club").build();

        List<Club> clubs = List.of(homeClub, awayClub, anotherClub);
        when(clubRepository.findAll()).thenReturn(clubs);

        when(random.nextInt(clubs.size())).thenReturn(0, 1);

        // When
        Club result = clubService.getRandomClub(homeClubId);

        // Then
        assertNotNull(result);
        assertNotEquals(homeClubId, result.getId());
        verify(clubRepository, times(1)).findAll();
        verify(random, atLeastOnce()).nextInt(clubs.size());
    }

    @Test
    void shouldThrowExceptionWhenTryingToFindDifferentThanHomeClubButNotEnoughClubs() {
        // Given
        UUID homeClubId = UUID.randomUUID();
        Club onlyClub = Club.builder().id(homeClubId).name("Only Club").build();
        when(clubRepository.findAll()).thenReturn(List.of(onlyClub));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clubService.getRandomClub(homeClubId));
        assertEquals("Not enough clubs", exception.getMessage());

        verify(clubRepository, times(1)).findAll();
        verify(random, never()).nextInt(anyInt());
    }

    @Test
    void shouldEditClubSuccessfully() {
        // Given
        UUID clubId = UUID.randomUUID();
        Club existingClub = Club.builder().id(clubId).name("Old Name").logo("old_logo.png").build();
        ClubEdit clubEdit = new ClubEdit("New Name", "new_logo.png");

        when(clubRepository.findById(clubId)).thenReturn(Optional.of(existingClub));

        // When
        clubService.editClub(clubId, clubEdit);

        // Then
        assertEquals("New Name", existingClub.getName());
        assertEquals("new_logo.png", existingClub.getLogo());

        verify(clubRepository, times(1)).findById(clubId);
        verify(clubRepository, times(1)).save(existingClub);
    }

    @Test
    void shouldThrowExceptionWhenTryingToEditAndClubNotFound() {
        // Given
        UUID clubId = UUID.randomUUID();
        ClubEdit clubEdit = new ClubEdit("New Name", "new_logo.png");
        when(clubRepository.findById(clubId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clubService.editClub(clubId, clubEdit));
        assertEquals("Club not found", exception.getMessage());

        verify(clubRepository, times(1)).findById(clubId);
        verify(clubRepository, never()).save(any());
    }

    @Test
    void shouldReturnMinusOneWhenClubNotFound() {
        // Given
        UUID clubId = UUID.randomUUID();
        Club club1 = Club.builder().id(UUID.randomUUID()).points(9).build();
        Club club2 = Club.builder().id(UUID.randomUUID()).points(6).build();

        List<Club> sortedClubs = List.of(club1, club2);

        when(clubRepository.findAllByOrderByPointsDesc()).thenReturn(sortedClubs);

        // When
        int position = clubService.getClubPosition(clubId);

        // Then
        assertEquals(-1, position);
    }

    @Test
    void shouldReturnFirstPositionWhenClubIsFirst() {
        // Given
        UUID clubId = UUID.randomUUID();
        Club club1 = Club.builder().id(clubId).points(10).build();
        Club club2 = Club.builder().id(UUID.randomUUID()).points(7).build();

        List<Club> sortedClubs = List.of(club1, club2);

        when(clubRepository.findAllByOrderByPointsDesc()).thenReturn(sortedClubs);

        // When
        int position = clubService.getClubPosition(clubId);

        // Then
        assertEquals(1, position);
    }

    @Test
    void shouldReturnLastPositionWhenClubIsLast() {
        // Given
        UUID clubId = UUID.randomUUID();
        Club club1 = Club.builder().id(UUID.randomUUID()).points(10).build();
        Club club2 = Club.builder().id(clubId).points(3).build();

        List<Club> sortedClubs = List.of(club1, club2);

        when(clubRepository.findAllByOrderByPointsDesc()).thenReturn(sortedClubs);

        // When
        int position = clubService.getClubPosition(clubId);

        // Then
        assertEquals(2, position);
    }

    @Test
    void shouldReturnMinusOneWhenListIsEmpty() {
        // Given
        when(clubRepository.findAllByOrderByPointsDesc()).thenReturn(List.of());

        // When
        int position = clubService.getClubPosition(UUID.randomUUID());

        // Then
        assertEquals(-1, position);
    }

    @Test
    void shouldDeductPaymentSuccessfully() {
        // Given
        UUID clubId = UUID.randomUUID();
        Club club = Club.builder().id(clubId).finances(BigDecimal.valueOf(5000)).build();
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        // When
        clubService.deductPayment(BigDecimal.valueOf(1000), clubId);

        // Then
        assertEquals(BigDecimal.valueOf(4000), club.getFinances());
        verify(clubRepository).save(club);
    }

    @Test
    void shouldThrowExceptionWhenTryingToDeductPaymentButClubNotFound() {
        // Given
        UUID clubId = UUID.randomUUID();
        when(clubRepository.findById(clubId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> clubService.deductPayment(BigDecimal.valueOf(1000), clubId));
        assertEquals("Club not found", exception.getMessage());
        verify(clubRepository, never()).save(any());
    }

    @Test
    void shouldNotChangeFinancesWhenDeductingZero() {
        // Given
        UUID clubId = UUID.randomUUID();
        Club club = Club.builder().id(clubId).finances(BigDecimal.valueOf(5000)).build();
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        // When
        clubService.deductPayment(BigDecimal.ZERO, clubId);

        // Then
        assertEquals(BigDecimal.valueOf(5000), club.getFinances());
        verify(clubRepository).save(club);
    }

    @Test
    void shouldIncreaseFinancesSuccessfully() {
        // Given
        UUID clubId = UUID.randomUUID();
        Club club = Club.builder().id(clubId).finances(BigDecimal.valueOf(5000)).build();
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        // When
        BigDecimal newBalance = clubService.increaseFinances(BigDecimal.valueOf(2000), clubId);

        // Then
        assertEquals(BigDecimal.valueOf(7000), newBalance);
        verify(clubRepository).save(club);
    }

    @Test
    void shouldThrowExceptionWhenTryingToIncreaseFinancesButClubNotFound() {
        // Given
        UUID clubId = UUID.randomUUID();
        when(clubRepository.findById(clubId)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> clubService.increaseFinances(BigDecimal.valueOf(2000), clubId));
        assertEquals("Club not found", exception.getMessage());
        verify(clubRepository, never()).save(any());
    }

    @Test
    void shouldNotChangeFinancesWhenAddingZero() {
        // Given
        UUID clubId = UUID.randomUUID();
        Club club = Club.builder().id(clubId).finances(BigDecimal.valueOf(5000)).build();
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(club));

        // When
        BigDecimal newBalance = clubService.increaseFinances(BigDecimal.ZERO, clubId);

        // Then
        assertEquals(BigDecimal.valueOf(5000), newBalance);
        verify(clubRepository).save(club);
    }

    @Test
    void shouldAwardThreePointsToWinnerAndDeductThreeFromLoser() {
        // Given
        Club winner = Club.builder().points(10).build();
        Club loser = Club.builder().points(7).build();

        // When
        clubService.setPoints(winner, loser);

        // Then
        assertEquals(13, winner.getPoints());
        assertEquals(4, loser.getPoints());
    }

    @Test
    void shouldNotSetNegativePointsForLoser() {
        // Given
        Club winner = Club.builder().points(10).build();
        Club loser = Club.builder().points(2).build();

        // When
        clubService.setPoints(winner, loser);

        // Then
        assertEquals(13, winner.getPoints());
        assertEquals(0, loser.getPoints());
    }

    @Test
    void shouldAwardOnePointToBothTeamsForDraw() {
        // Given
        Club homeClub = Club.builder().points(10).build();
        Club awayClub = Club.builder().points(7).build();

        // When
        clubService.setDrawPoints(homeClub, awayClub);

        // Then
        assertEquals(11, homeClub.getPoints());
        assertEquals(8, awayClub.getPoints());
    }
}
