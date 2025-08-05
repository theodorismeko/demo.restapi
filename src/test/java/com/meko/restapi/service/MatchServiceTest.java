package com.meko.restapi.service;

import com.meko.restapi.dto.MatchDTO;
import com.meko.restapi.entity.Match;
import com.meko.restapi.enumeration.Sport;
import com.meko.restapi.exception.ResourceNotFoundException;
import com.meko.restapi.repository.MatchRepository;
import com.meko.restapi.service.impl.MatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchServiceImpl matchService;

    private Match match;
    private MatchDTO matchDTO;

    @BeforeEach
    void setUp() {
        match = new Match();
        match.setId(1L);
        match.setDescription("Test Match");
        match.setMatchDate(LocalDate.now());
        match.setMatchTime(LocalTime.of(20, 0));
        match.setTeamA("Team A");
        match.setTeamB("Team B");
        match.setSport(Sport.FOOTBALL);

        matchDTO = new MatchDTO();
        matchDTO.setId(1L);
        matchDTO.setDescription("Test Match");
        matchDTO.setMatchDate(LocalDate.now());
        matchDTO.setMatchTime(LocalTime.of(20, 0));
        matchDTO.setTeamA("Team A");
        matchDTO.setTeamB("Team B");
        matchDTO.setSport(Sport.FOOTBALL);
    }

    @Test
    void getMatchById_ShouldReturnMatch_WhenMatchExists() {
        // Given
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        // When
        MatchDTO result = matchService.getMatchById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Match", result.getDescription());
        assertEquals("Team A", result.getTeamA());
        assertEquals("Team B", result.getTeamB());
        assertEquals(Sport.FOOTBALL, result.getSport());
        verify(matchRepository).findById(1L);
    }

    @Test
    void getMatchById_ShouldThrowResourceNotFoundException_WhenMatchDoesNotExist() {
        // Given
        when(matchRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> matchService.getMatchById(1L));
        verify(matchRepository).findById(1L);
    }

    @Test
    void getAllMatches_ShouldReturnAllMatches() {
        // Given
        List<Match> matches = Arrays.asList(match);
        when(matchRepository.findAll()).thenReturn(matches);

        // When
        List<MatchDTO> result = matchService.getAllMatches();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Match", result.get(0).getDescription());
        assertEquals("Team A", result.get(0).getTeamA());
        assertEquals("Team B", result.get(0).getTeamB());
        verify(matchRepository).findAll();
    }

    @Test
    void createMatch_ShouldSaveAndReturnMatch() {
        // Given
        Match savedMatch = new Match();
        savedMatch.setId(1L);
        savedMatch.setDescription("Test Match");
        savedMatch.setMatchDate(LocalDate.now());
        savedMatch.setMatchTime(LocalTime.of(20, 0));
        savedMatch.setTeamA("Team A");
        savedMatch.setTeamB("Team B");
        savedMatch.setSport(Sport.FOOTBALL);
        
        when(matchRepository.save(any(Match.class))).thenReturn(savedMatch);

        // When
        MatchDTO result = matchService.createMatch(matchDTO);

        // Then
        assertNotNull(result);
        assertEquals("Test Match", result.getDescription());
        assertEquals("Team A", result.getTeamA());
        assertEquals("Team B", result.getTeamB());
        verify(matchRepository).save(any(Match.class));
    }

    @Test
    void updateMatch_ShouldUpdateExistingMatch() {
        // Given
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        MatchDTO updateDTO = new MatchDTO();
        updateDTO.setDescription("Updated Match");
        updateDTO.setTeamA("Updated Team A");
        updateDTO.setTeamB("Team B");
        updateDTO.setMatchDate(LocalDate.now());
        updateDTO.setMatchTime(LocalTime.of(20, 0));
        updateDTO.setSport(Sport.FOOTBALL);

        // When
        MatchDTO result = matchService.updateMatch(1L, updateDTO);

        // Then
        assertNotNull(result);
        verify(matchRepository).findById(1L);
        verify(matchRepository).save(match);
        assertEquals("Updated Match", match.getDescription());
        assertEquals("Updated Team A", match.getTeamA());
    }

    @Test
    void deleteMatch_ShouldRemoveMatch_WhenMatchExists() {
        // Given
        when(matchRepository.existsById(1L)).thenReturn(true);

        // When
        matchService.deleteMatch(1L);

        // Then
        verify(matchRepository).existsById(1L);
        verify(matchRepository).deleteById(1L);
    }

    @Test
    void deleteMatch_ShouldThrowResourceNotFoundException_WhenMatchDoesNotExist() {
        // Given
        when(matchRepository.existsById(1L)).thenReturn(false);

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> matchService.deleteMatch(1L));
        verify(matchRepository).existsById(1L);
        verify(matchRepository, never()).deleteById(anyLong());
    }
}