package com.meko.restapi.entity;

import com.meko.restapi.enumeration.Sport;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "matchOdds")
@ToString(exclude = "matchOdds")
public class Match {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String description;
    
    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;
    
    @Column(name = "match_time", nullable = false)
    private LocalTime matchTime;
    
    @Column(name = "team_a", nullable = false)
    private String teamA;
    
    @Column(name = "team_b", nullable = false)
    private String teamB;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sport sport;
    
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchOdds> matchOdds = new ArrayList<>();
}
