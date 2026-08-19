package com.cricket.scorecard.controller;

import com.cricket.scorecard.dto.CreateMatchRequest;
import com.cricket.scorecard.dto.RecordBallRequest;
import com.cricket.scorecard.model.Match;
import com.cricket.scorecard.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller exposing HTTP Endpoints for Match Management & Live Scorecard Engine.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @RestController: Indicates this class handles HTTP REST requests and serializes output to JSON.
 * - @RequestMapping("/api/matches"): Base endpoint path for all match API endpoints.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/matches")
public class MatchController {


    private final MatchService matchService;

    /**
     * Constructor Injection for MatchService.
     * 
     * @param matchService Injected match business service bean.
     */
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Endpoint to create/start a new cricket match.
     * 
     * DEVELOPER NOTES & ANNOTATIONS:
     * - @PostMapping: Handles HTTP POST to "/api/matches".
     * - Returns HTTP Status 201 CREATED upon success.
     * 
     * @param request Match initialization payload.
     * @return ResponseEntity with created Match JSON and HTTP 201 status.
     */
    @PostMapping
    public ResponseEntity<Match> createMatch(@RequestBody CreateMatchRequest request) {
        Match match = matchService.createMatch(request);
        return new ResponseEntity<>(match, HttpStatus.CREATED);
    }

    /**
     * Endpoint to record a ball delivery event in an active match.
     * 
     * DEVELOPER NOTES & ANNOTATIONS:
     * - @PostMapping("/{id}/ball"): Handles HTTP POST to "/api/matches/{id}/ball".
     * - @PathVariable String id: Dynamic URI parameter representing the match document ID.
     * - Returns HTTP Status 200 OK with updated Match scorecard JSON.
     * 
     * @param id Target match ID.
     * @param request Ball event payload.
     * @return ResponseEntity with updated Match JSON and HTTP 200 OK status.
     */
    @PostMapping("/{id}/ball")
    public ResponseEntity<Match> recordBall(@PathVariable String id, @RequestBody RecordBallRequest request) {
        Match updatedMatch = matchService.recordBall(id, request);
        return ResponseEntity.ok(updatedMatch);
    }


    /**
     * Endpoint to fetch the live scorecard state of a match.
     * 
     * DEVELOPER NOTES & ANNOTATIONS:
     * - @GetMapping("/{id}"): Handles HTTP GET requests to "/api/matches/{id}".
     * 
     * @param id Target match ID.
     * @return ResponseEntity with current Match state JSON and HTTP 200 OK status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Match> getScorecard(@PathVariable String id) {
        Match match = matchService.getMatchById(id);
        return ResponseEntity.ok(match);
    }
}

