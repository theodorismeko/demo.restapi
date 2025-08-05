package com.meko.restapi.service;

import com.meko.restapi.dto.MatchDTO;
import com.meko.restapi.dto.MatchOddsDTO;

import java.util.List;

public interface MatchService {
    
    MatchDTO createMatch(MatchDTO matchDTO);
    MatchDTO getMatchById(Long id);
    List<MatchDTO> getAllMatches();
    MatchDTO updateMatch(Long id, MatchDTO matchDTO);
    void deleteMatch(Long id);

}
