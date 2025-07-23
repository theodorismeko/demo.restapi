package com.meko.restapi.repository;

import com.meko.restapi.entity.Match;
import com.meko.restapi.enumeration.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    
    List<Match> findByMatchDate(LocalDate matchDate);
    
    List<Match> findBySport(Sport sport);
    
    List<Match> findByTeamAOrTeamB(String teamA, String teamB);
}
