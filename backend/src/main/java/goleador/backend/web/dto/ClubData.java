package goleador.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClubData {
    private String clubName;
    private LocalDate createdOn;
    private UUID managerId;
    private String logo;
    private List<UUID> playersIds;
    private List<UUID> homeGamesIds;
    private List<UUID> awayGamesIds;
    private int points;
    private BigDecimal finances;
}
