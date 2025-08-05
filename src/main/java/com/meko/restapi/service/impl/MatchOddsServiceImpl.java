package com.meko.restapi.service.impl;

import com.meko.restapi.dto.MatchOddsDTO;
import com.meko.restapi.entity.Match;
import com.meko.restapi.entity.MatchOdds;
import com.meko.restapi.exception.ResourceNotFoundException;
import com.meko.restapi.repository.MatchOddsRepository;
import com.meko.restapi.repository.MatchRepository;
import com.meko.restapi.service.MatchOddsService;
import com.meko.restapi.util.DtoEntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchOddsServiceImpl implements MatchOddsService {

    private final MatchRepository matchRepository;
    private final MatchOddsRepository matchOddsRepository;
    @Override
    public MatchOddsDTO createMatchOdds(MatchOddsDTO matchOddsDTO) {
        Match match = matchRepository.findById(matchOddsDTO.getMatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Match", "id", matchOddsDTO.getMatchId()));

        MatchOdds matchOdds = new MatchOdds();
        matchOdds.setMatch(match);
        matchOdds.setSpecifier(matchOddsDTO.getSpecifier());
        matchOdds.setOdd(matchOddsDTO.getOdd());

        matchOdds = matchOddsRepository.save(matchOdds);
        return DtoEntityConverter.convertToDTO(matchOdds);
    }

    @Override
    @Transactional(readOnly = true)
    public MatchOddsDTO getMatchOddsById(Long id) {
        MatchOdds matchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MatchOdds", "id", id));
        return DtoEntityConverter.convertToDTO(matchOdds);
    }

    @Override
    public List<MatchOddsDTO> getMatchOddsByMatchId(Long matchId) {
        return matchOddsRepository.findByMatchId(matchId).stream()
                .map(DtoEntityConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MatchOddsDTO updateMatchOdds(Long id, MatchOddsDTO matchOddsDTO) {
        MatchOdds matchOdds = matchOddsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MatchOdds", "id", id));

        matchOdds.setSpecifier(matchOddsDTO.getSpecifier());
        matchOdds.setOdd(matchOddsDTO.getOdd());

        matchOdds = matchOddsRepository.save(matchOdds);
        return DtoEntityConverter.convertToDTO(matchOdds);
    }

    @Override
    public void deleteMatchOdds(Long id) {
        if (!matchOddsRepository.existsById(id)) {
            throw new ResourceNotFoundException("MatchOdds", "id", id);
        }
        matchOddsRepository.deleteById(id);
    }
}
