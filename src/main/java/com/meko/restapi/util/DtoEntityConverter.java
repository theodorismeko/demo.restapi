package com.meko.restapi.util;

import com.meko.restapi.dto.MatchDTO;
import com.meko.restapi.dto.MatchOddsDTO;
import com.meko.restapi.entity.Match;
import com.meko.restapi.entity.MatchOdds;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for converting between entity and DTO objects.
 * Used by both Match and MatchOdds service implementations.
 */
public class DtoEntityConverter {

    /**
     * Convert a Match entity to a MatchDTO
     */
    public static MatchDTO convertToDTO(Match match) {
        if (match == null) {
            return null;
        }

        MatchDTO dto = new MatchDTO();
        dto.setId(match.getId());
        dto.setDescription(match.getDescription());
        dto.setMatchDate(match.getMatchDate());
        dto.setMatchTime(match.getMatchTime());
        dto.setTeamA(match.getTeamA());
        dto.setTeamB(match.getTeamB());
        dto.setSport(match.getSport());

        if (match.getMatchOdds() != null) {
            List<MatchOddsDTO> oddsDTOs = match.getMatchOdds().stream()
                    .map(DtoEntityConverter::convertToDTO)
                    .collect(Collectors.toList());
            dto.setMatchOdds(oddsDTOs);
        }

        return dto;
    }

    /**
     * Convert a MatchDTO to a Match entity
     */
    public static Match convertToEntity(MatchDTO dto) {
        if (dto == null) {
            return null;
        }

        Match match = new Match();
        match.setDescription(dto.getDescription());
        match.setMatchDate(dto.getMatchDate());
        match.setMatchTime(dto.getMatchTime());
        match.setTeamA(dto.getTeamA());
        match.setTeamB(dto.getTeamB());
        match.setSport(dto.getSport());
        return match;
    }

    /**
     * Convert a MatchOdds entity to a MatchOddsDTO
     */
    public static MatchOddsDTO convertToDTO(MatchOdds matchOdds) {
        if (matchOdds == null) {
            return null;
        }

        MatchOddsDTO dto = new MatchOddsDTO();
        dto.setId(matchOdds.getId());
        dto.setMatchId(matchOdds.getMatch().getId());
        dto.setSpecifier(matchOdds.getSpecifier());
        dto.setOdd(matchOdds.getOdd());
        return dto;
    }

    /**
     * Convert a MatchOddsDTO to a MatchOdds entity
     * Note: This method doesn't set the Match property, which should be done by the service
     */
    public static MatchOdds convertToEntity(MatchOddsDTO dto) {
        if (dto == null) {
            return null;
        }

        MatchOdds matchOdds = new MatchOdds();
        matchOdds.setSpecifier(dto.getSpecifier());
        matchOdds.setOdd(dto.getOdd());
        return matchOdds;
    }
}

