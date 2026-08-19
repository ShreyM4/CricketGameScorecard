package com.cricket.scorecard.controller;

import com.cricket.scorecard.dto.CreatePlayerRequest;
import com.cricket.scorecard.model.Player;
import com.cricket.scorecard.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing HTTP Endpoints for Player Management.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @RestController: Combination of @Controller and @ResponseBody. Tells Spring that return values
 *   from methods should be serialized directly into JSON HTTP response bodies.
 * - @RequestMapping("/api/players"): Sets the base URI path for all endpoints in this controller.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/players")
public class PlayerController {


    private final PlayerService playerService;

    /**
     * Constructor Injection for PlayerService.
     * 
     * @param playerService Injected business service.
     */
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Endpoint to register a new player.
     * 
     * DEVELOPER NOTES & ANNOTATIONS:
     * - @PostMapping: Maps HTTP POST requests to "/api/players".
     * - @RequestBody: Deserializes incoming JSON request body into the CreatePlayerRequest DTO.
     * - ResponseEntity: Wraps the response payload and specifies HTTP status code (201 CREATED).
     * 
     * @param request Player creation request payload.
     * @return ResponseEntity with created Player JSON and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody CreatePlayerRequest request) {
        Player createdPlayer = playerService.createPlayer(request);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }


    /**
     * Endpoint to fetch all registered players.
     * 
     * DEVELOPER NOTES & ANNOTATIONS:
     * - @GetMapping: Maps HTTP GET requests to "/api/players".
     * 
     * @return ResponseEntity containing List of Player JSON objects with HTTP 200 OK status.
     */
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    /**
     * Endpoint to fetch a single player by unique ID.
     * 
     * DEVELOPER NOTES & ANNOTATIONS:
     * - @GetMapping("/{id}"): Maps HTTP GET requests to "/api/players/{id}".
     * - @PathVariable String id: Extracts dynamic URI path segment {id} into Java String parameter.
     * 
     * @param id Player document ID.
     * @return ResponseEntity containing Player JSON object with HTTP 200 OK status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable String id) {
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }
}

