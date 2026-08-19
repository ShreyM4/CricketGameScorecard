package com.cricket.scorecard.controller;

import com.cricket.scorecard.dto.CreateMatchRequest;
import com.cricket.scorecard.dto.RecordBallRequest;
import com.cricket.scorecard.model.Match;
import com.cricket.scorecard.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchController.class)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MatchService matchService;

    @Test
    @DisplayName("POST /api/matches - Should create a new match")
    void testCreateMatch() throws Exception {
        CreateMatchRequest request = new CreateMatchRequest("IND", "AUS", 20);
        Match createdMatch = new Match("m1", "IND", "AUS", 20, null, null);

        when(matchService.createMatch(any(CreateMatchRequest.class))).thenReturn(createdMatch);

        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("m1"))
                .andExpect(jsonPath("$.battingTeam").value("IND"))
                .andExpect(jsonPath("$.bowlingTeam").value("AUS"))
                .andExpect(jsonPath("$.totalOvers").value(20))
                .andExpect(jsonPath("$.status").value("LIVE"));
    }

    @Test
    @DisplayName("POST /api/matches/{id}/ball - Should record ball event")
    void testRecordBall() throws Exception {
        RecordBallRequest ballRequest = new RecordBallRequest(4, false, false);
        Match updatedMatch = new Match("m1", "IND", "AUS", 20, null, null);
        updatedMatch.setTotalRuns(4);
        updatedMatch.setOversBowled(0.1);

        when(matchService.recordBall(eq("m1"), any(RecordBallRequest.class))).thenReturn(updatedMatch);

        mockMvc.perform(post("/api/matches/m1/ball")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ballRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRuns").value(4))
                .andExpect(jsonPath("$.oversBowled").value(0.1));
    }

    @Test
    @DisplayName("GET /api/matches/{id} - Should return live scorecard")
    void testGetScorecard() throws Exception {
        Match match = new Match("m1", "IND", "AUS", 20, null, null);
        match.setTotalRuns(45);
        match.setTotalWickets(2);
        match.setOversBowled(5.3);

        when(matchService.getMatchById("m1")).thenReturn(match);

        mockMvc.perform(get("/api/matches/m1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("m1"))
                .andExpect(jsonPath("$.totalRuns").value(45))
                .andExpect(jsonPath("$.totalWickets").value(2))
                .andExpect(jsonPath("$.oversBowled").value(5.3));
    }
}
