package com.meko.restapi.service;

import com.meko.restapi.dto.MatchDTO;
import com.meko.restapi.dto.MatchOddsDTO;

import java.util.List;

public interface MatchService {
    
    // Match CRUD operations
    MatchDTO createMatch(MatchDTO matchDTO);
    MatchDTO getMatchById(Long id);
    List<MatchDTO> getAllMatches();
    MatchDTO updateMatch(Long id, MatchDTO matchDTO);
    void deleteMatch(Long id);
    
    // MatchOdds CRUD operations
    MatchOddsDTO createMatchOdds(MatchOddsDTO matchOddsDTO);
    MatchOddsDTO getMatchOddsById(Long id);
    List<MatchOddsDTO> getMatchOddsByMatchId(Long matchId);
    MatchOddsDTO updateMatchOdds(Long id, MatchOddsDTO matchOddsDTO);
    void deleteMatchOdds(Long id);
}
