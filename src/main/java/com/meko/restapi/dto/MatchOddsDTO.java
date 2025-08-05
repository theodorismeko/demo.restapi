package com.meko.restapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Betting odds information for a match")
public class MatchOddsDTO {
    @Schema(description = "Unique identifier of the odds", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    
    @NotNull(message = "Match ID is required")
    @Schema(description = "ID of the associated match", example = "1")
    private Long matchId;
    
    @NotBlank(message = "Specifier is required")
    @Schema(description = "Betting specifier (e.g., X for draw, 1 for home win, 2 for away win)", example = "X")
    private String specifier;
    
    @NotNull(message = "Odd is required")
    @Positive(message = "Odd must be positive")
    @Schema(description = "Betting odd value", example = "1.5", minimum = "0.01")
    private Double odd;
}
