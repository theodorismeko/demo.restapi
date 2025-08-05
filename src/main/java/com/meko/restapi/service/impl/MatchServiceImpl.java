package com.meko.restapi.service.impl;

import com.meko.restapi.dto.MatchDTO;
import com.meko.restapi.entity.Match;
import com.meko.restapi.exception.ResourceNotFoundException;
import com.meko.restapi.repository.MatchRepository;
import com.meko.restapi.service.MatchService;
import com.meko.restapi.util.DtoEntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.meko.restapi.util.DtoEntityConverter.convertToEntity;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchServiceImpl implements MatchService {
    
    private final MatchRepository matchRepository;
    @Override
    public MatchDTO createMatch(MatchDTO matchDTO) {
        Match match = convertToEntity(matchDTO);
        match = matchRepository.save(match);
        return DtoEntityConverter.convertToDTO(match);
    }
    
    @Override
    @Transactional(readOnly = true)
    public MatchDTO getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "id", id));
        return DtoEntityConverter.convertToDTO(match);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MatchDTO> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(DtoEntityConverter::convertToDTO)
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
        return DtoEntityConverter.convertToDTO(match);
    }
    
    @Override
    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Match", "id", id);
        }
        matchRepository.deleteById(id);
    }
}
