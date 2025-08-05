package com.meko.restapi.controller;

import com.meko.restapi.dto.MatchOddsDTO;
import com.meko.restapi.service.MatchOddsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match-odds")
@RequiredArgsConstructor
@Tag(name = "Match Odds Management", description = "API endpoints for managing match betting odds")
public class MatchOddsController {
    
    private final MatchOddsService matchOddsService;
    
    @Operation(summary = "Create match odds", description = "Creates new betting odds for a specific match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Match odds created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatchOddsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<MatchOddsDTO> createMatchOdds(@Valid @RequestBody MatchOddsDTO matchOddsDTO) {
        MatchOddsDTO createdOdds = matchOddsService.createMatchOdds(matchOddsDTO);
        return new ResponseEntity<>(createdOdds, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Get match odds by ID", description = "Retrieves specific match odds by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match odds found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatchOddsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Match odds not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MatchOddsDTO> getMatchOddsById(
            @Parameter(description = "ID of the match odds to retrieve", required = true)
            @PathVariable Long id) {
        MatchOddsDTO matchOdds = matchOddsService.getMatchOddsById(id);
        return ResponseEntity.ok(matchOdds);
    }
    
    @Operation(summary = "Get match odds by match ID", description = "Retrieves all match odds for a specific match")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match odds retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MatchOddsDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Match not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<MatchOddsDTO>> getMatchOddsByMatchId(
            @Parameter(description = "ID of the match for which to retrieve odds", required = true)
            @PathVariable Long matchId) {
        List<MatchOddsDTO> matchOdds = matchOddsService.getMatchOddsByMatchId(matchId);
        return ResponseEntity.ok(matchOdds);
    }
    
    @Operation(summary = "Update match odds", description = "Updates existing betting odds")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Match odds updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MatchOddsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Match odds not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MatchOddsDTO> updateMatchOdds(
            @Parameter(description = "ID of the match odds to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody MatchOddsDTO matchOddsDTO) {
        MatchOddsDTO updatedOdds = matchOddsService.updateMatchOdds(id, matchOddsDTO);
        return ResponseEntity.ok(updatedOdds);
    }
    
    @Operation(summary = "Delete match odds", description = "Deletes specific match odds by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Match odds deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Match odds not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatchOdds(
            @Parameter(description = "ID of the match odds to delete", required = true)
            @PathVariable Long id) {
        matchOddsService.deleteMatchOdds(id);
        return ResponseEntity.noContent().build();
    }
}
