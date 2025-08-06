package com.meko.restapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "match_odds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "match")
public class MatchOdds {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    @JsonIgnore
    private Match match;
    
    @Column(nullable = false)
    private String specifier;
    
    @Column(nullable = false)
    private Double odd;
}
