package com.meko.restapi.service;
import com.meko.restapi.dto.MatchOddsDTO;

import java.util.List;

public interface MatchOddsService {

    MatchOddsDTO createMatchOdds(MatchOddsDTO matchOddsDTO);
    MatchOddsDTO getMatchOddsById(Long id);
    List<MatchOddsDTO> getMatchOddsByMatchId(Long matchId);
    MatchOddsDTO updateMatchOdds(Long id, MatchOddsDTO matchOddsDTO);
    void deleteMatchOdds(Long id);
}
