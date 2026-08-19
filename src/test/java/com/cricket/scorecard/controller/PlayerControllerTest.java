package com.cricket.scorecard.controller;

import com.cricket.scorecard.dto.CreatePlayerRequest;
import com.cricket.scorecard.model.Player;
import com.cricket.scorecard.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlayerService playerService;

    @Test
    @DisplayName("POST /api/players - Should create a new player")
    void testCreatePlayer() throws Exception {
        CreatePlayerRequest request = new CreatePlayerRequest("Virat Kohli", "BATSMAN");
        Player createdPlayer = new Player("p1", "Virat Kohli", "BATSMAN");

        when(playerService.createPlayer(any(CreatePlayerRequest.class))).thenReturn(createdPlayer);

        mockMvc.perform(post("/api/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("p1"))
                .andExpect(jsonPath("$.name").value("Virat Kohli"))
                .andExpect(jsonPath("$.role").value("BATSMAN"));
    }

    @Test
    @DisplayName("GET /api/players - Should return list of players")
    void testGetAllPlayers() throws Exception {
        Player p1 = new Player("p1", "Virat Kohli", "BATSMAN");
        Player p2 = new Player("p2", "Jasprit Bumrah", "BOWLER");

        when(playerService.getAllPlayers()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Virat Kohli"))
                .andExpect(jsonPath("$[1].name").value("Jasprit Bumrah"));
    }
}
