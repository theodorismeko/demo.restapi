package com.meko.restapi.dto;

import com.meko.restapi.enumeration.Sport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Match information with associated betting odds")
public class MatchDTO {
    @Schema(description = "Unique identifier of the match", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotBlank(message = "Description is required")
    @Schema(description = "Match description", example = "OSFP-PAO", required = true)
    private String description;
    
    @NotNull(message = "Match date is required")
    @Schema(description = "Date of the match", example = "2024-03-31", required = true)
    private LocalDate matchDate;
    
    @NotNull(message = "Match time is required")
    @Schema(description = "Time of the match", example = "12:00", required = true)
    private LocalTime matchTime;
    
    @NotBlank(message = "Team A is required")
    @Schema(description = "First team name", example = "OSFP", required = true)
    private String teamA;
    
    @NotBlank(message = "Team B is required")
    @Schema(description = "Second team name", example = "PAO", required = true)
    private String teamB;
    
    @NotNull(message = "Sport is required")
    @Schema(description = "Type of sport", example = "FOOTBALL", required = true)
    private Sport sport;
    
    @Schema(description = "List of betting odds for this match")
    private List<MatchOddsDTO> matchOdds;
}
