package goleador.backend.web;

import goleador.backend.domain.club.model.Club;
import goleador.backend.domain.club.service.ClubService;
import goleador.backend.web.dto.ClubData;
import goleador.backend.web.mapper.ClubMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
