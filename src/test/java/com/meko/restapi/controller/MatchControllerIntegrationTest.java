package com.meko.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meko.restapi.dto.MatchDTO;
import com.meko.restapi.enumeration.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class MatchControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMatchShouldReturnCreatedWhenValidInput() throws Exception {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setDescription("Premier League: Arsenal vs Manchester United");
        matchDTO.setMatchDate(LocalDate.now().plusDays(7));
        matchDTO.setMatchTime(LocalTime.of(15, 30));
        matchDTO.setTeamA("Arsenal");
        matchDTO.setTeamB("Manchester United");
        matchDTO.setSport(Sport.FOOTBALL);

        mockMvc.perform(post("/api/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matchDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Premier League: Arsenal vs Manchester United"))
                .andExpect(jsonPath("$.teamA").value("Arsenal"))
                .andExpect(jsonPath("$.teamB").value("Manchester United"))
                .andExpect(jsonPath("$.sport").value("FOOTBALL"));
    }

    @Test
    void getAllMatchesShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createMatchShouldReturnBadRequestWhenInvalidInput() throws Exception {
        MatchDTO invalidMatch = new MatchDTO();
        // Missing required fields

        mockMvc.perform(post("/api/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidMatch)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getMatchShouldReturnNotFoundWhenMatchDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/matches/999999"))
                .andExpect(status().isNotFound());
    }
}