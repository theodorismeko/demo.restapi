package com.meko.restapi.service.impl;

import com.meko.restapi.dto.MatchDTO;
import com.meko.restapi.dto.MatchOddsDTO;
import com.meko.restapi.entity.Match;
import com.meko.restapi.entity.MatchOdds;
import com.meko.restapi.exception.ResourceNotFoundException;
import com.meko.restapi.repository.MatchOddsRepository;
import com.meko.restapi.repository.MatchRepository;
import com.meko.restapi.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MatchServiceImpl implements MatchService {
    
    private final MatchRepository matchRepository;
    private final MatchOddsRepository matchOddsRepository;
    
    @Override
    public MatchDTO createMatch(MatchDTO matchDTO) {
        Match match = convertToEntity(matchDTO);
        match = matchRepository.save(match);
        return convertToDTO(match);
    }
    
    @Override
    @Transactional(readOnly = true)
    public MatchDTO getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "id", id));
        return convertToDTO(match);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MatchDTO> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public MatchDTO updateMatch(Long id, MatchDTO matchDTO) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "id", id));
        
        match.setDescription(matchDTO.getDescription());
        match.setMatchDate(matchDTO.getMatchDate());
        match.setMatchTime(matchDTO.getMatchTime());
        match.setTeamA(matchDTO.getTeamA());
        match.setTeamB(matchDTO.getTeamB());
        match.setSport(matchDTO.getSport());
        
        match = matchRepository.save(match);
        return convertToDTO(match);
    }
    
    @Override
    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Match", "id", id);
        }
        matchRepository.deleteById(id);
    }
    
    @Override
    public MatchOddsDTO createMatchOdds(MatchOddsDTO matchOddsDTO) {
        Match match = matchRepository.findById(matchOddsDTO.getMatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Match", "id", matchOddsDTO.getMatchId()));
        
        MatchOdds matchOdds = new MatchOdds();
        matchOdds.setMatch(match);
        matchOdds.setSpecifier(matchOddsDTO.getSpecifier());
        matchOdds.setOdd(matchOddsDTO.getOdd());
        
        matchOdds = matchOddsRepository.save(matchOdds);
        return convertToDTO(matchOdds);
    }
    
    @Override
    @Transactional(readOnly = true)
    public MatchOddsDTO getMatchOddsById(Long id) {
        MatchOdds matchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MatchOdds", "id", id));
        return convertToDTO(matchOdds);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MatchOddsDTO> getMatchOddsByMatchId(Long matchId) {
        return matchOddsRepository.findByMatchId(matchId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public MatchOddsDTO updateMatchOdds(Long id, MatchOddsDTO matchOddsDTO) {
        MatchOdds matchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MatchOdds", "id", id));
        
        matchOdds.setSpecifier(matchOddsDTO.getSpecifier());
        matchOdds.setOdd(matchOddsDTO.getOdd());
        
        matchOdds = matchOddsRepository.save(matchOdds);
        return convertToDTO(matchOdds);
    }
    
    @Override
    public void deleteMatchOdds(Long id) {
        if (!matchOddsRepository.existsById(id)) {
            throw new ResourceNotFoundException("MatchOdds", "id", id);
        }
        matchOddsRepository.deleteById(id);
    }
    
    // Helper methods for conversion
    private MatchDTO convertToDTO(Match match) {
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
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            dto.setMatchOdds(oddsDTOs);
        }
        
        return dto;
    }
    
    private Match convertToEntity(MatchDTO dto) {
        Match match = new Match();
        match.setDescription(dto.getDescription());
        match.setMatchDate(dto.getMatchDate());
        match.setMatchTime(dto.getMatchTime());
        match.setTeamA(dto.getTeamA());
        match.setTeamB(dto.getTeamB());
        match.setSport(dto.getSport());
        return match;
    }
    
    private MatchOddsDTO convertToDTO(MatchOdds matchOdds) {
        MatchOddsDTO dto = new MatchOddsDTO();
        dto.setId(matchOdds.getId());
        dto.setMatchId(matchOdds.getMatch().getId());
        dto.setSpecifier(matchOdds.getSpecifier());
        dto.setOdd(matchOdds.getOdd());
        return dto;
    }
}
