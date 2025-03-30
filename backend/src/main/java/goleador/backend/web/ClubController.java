package goleador.backend.web;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.service.ClubService;
import goleador.backend.web.dto.*;
import goleador.backend.web.mapper.ClubMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final ClubMapper clubMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ClubData> getClub(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        Club club = clubService.getClubById(uuid);
        ClubData clubData = clubMapper.toClubData(club);
        return ResponseEntity.ok(clubData);
    }

    @PutMapping("/{clubId}")
    public ResponseEntity<Void> updateClub(@PathVariable String clubId, @RequestBody ClubEdit clubEdit) {
        UUID uuid = UUID.fromString(clubId);
        clubService.editClub(uuid, clubEdit);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/club-position")
    public ResponseEntity<BigDecimal> getClubPosition(@RequestParam String clubId) {
        UUID uuid = UUID.fromString(clubId);
        int position = clubService.getClubPosition(uuid);
        return ResponseEntity.ok(new BigDecimal(position));
    }

    @PostMapping("/increase-finances")
    public ResponseEntity<BigDecimal> getIncreaseFinances(@RequestBody IncreaseFinances increaseFinances) {
        UUID uuid = UUID.fromString(increaseFinances.getClubId());
        BigDecimal bigDecimal = clubService.increaseFinances(increaseFinances.getAmount(), uuid);
        return ResponseEntity.ok(bigDecimal);
    }

    @GetMapping("/eternal-ranking")
    public ResponseEntity<List<RankingRequest>> getEternalRanking() {
        List<Club> clubs = clubService.getAllClubsByPointsDesc();

        List<RankingRequest> rankingRequests = clubs.stream()
                .map(clubMapper::toRankingRequest)
                .toList();

        return ResponseEntity.ok(rankingRequests);
    }
}
