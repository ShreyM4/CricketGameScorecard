package com.cricket.scorecard.service;

import com.cricket.scorecard.dto.CreateMatchRequest;
import com.cricket.scorecard.dto.RecordBallRequest;
import com.cricket.scorecard.exception.InvalidMatchStateException;
import com.cricket.scorecard.exception.ResourceNotFoundException;
import com.cricket.scorecard.model.BallEvent;
import com.cricket.scorecard.model.Match;
import com.cricket.scorecard.repository.MatchRepository;
import org.springframework.stereotype.Service;

/**
 * Service Layer component encapsulating the Core Scoring & Game Engine Business Rules.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @Service: Registers this class as a business service bean in the Spring container.
 * - Responsibilities:
 *   1. Initializing match state (teams, overs limit, initial strikers).
 *   2. Validating match state prior to delivery execution.
 *   3. Calculating total runs (including extra penalty runs for Wides/No-Balls).
 *   4. Formatting legal overs count (e.g. 0.5, 1.0).
 *   5. Executing strike rotation algorithm (odd runs scored & over completions).
 *   6. Evaluating match completion conditions (10 wickets or total overs limit reached).
 */
@Service
public class MatchService {

    private final MatchRepository matchRepository;

    /**
     * Constructor Injection for MatchRepository.
     * 
     * @param matchRepository Injected repository interface.
     */
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    /**
     * Initializes and persists a new single-innings Match document.
     * Includes simple explicit Java validation checks for match parameters.
     * 
     * @param request CreateMatchRequest payload.
     * @return Created Match domain object with initial "LIVE" status.
     */
    public Match createMatch(CreateMatchRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Match request cannot be null");
        }
        if (request.getBattingTeam() == null || request.getBattingTeam().trim().isEmpty()) {
            throw new IllegalArgumentException("Batting team is required");
        }
        if (request.getBowlingTeam() == null || request.getBowlingTeam().trim().isEmpty()) {
            throw new IllegalArgumentException("Bowling team is required");
        }
        if (request.getTotalOvers() < 1) {
            throw new IllegalArgumentException("Total overs must be at least 1");
        }

        Match match = new Match();
        match.setBattingTeam(request.getBattingTeam());
        match.setBowlingTeam(request.getBowlingTeam());
        match.setTotalOvers(request.getTotalOvers());
        match.setStrikerId(request.getStrikerId());
        match.setNonStrikerId(request.getNonStrikerId());
        match.setStatus("LIVE");
        match.setTotalRuns(0);
        match.setTotalWickets(0);
        match.setOversBowled(0.0);
        match.setValidBalls(0);
        return matchRepository.save(match);
    }

    /**
     * Retrieves a Match document by unique ID.
     * Throws ResourceNotFoundException (mapped to HTTP 404) if match ID does not exist.
     * 
     * @param matchId Target match ID.
     * @return Match entity.
     */
    public Match getMatchById(String matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + matchId));
    }

    /**
     * Core Ball-by-Ball Processing Engine.
     * Processes a single delivery event, updates score state, manages strike rotation, and checks completion.
     * 
     * @param matchId Target match ID.
     * @param request Ball event request containing runsScored, isWicket, and isExtra.
     * @return Updated and persisted Match entity.
     */
    public Match recordBall(String matchId, RecordBallRequest request) {
        if (request == null || request.getRunsScored() < 0) {
            throw new IllegalArgumentException("Runs scored cannot be negative");
        }

        Match match = getMatchById(matchId);

        // STEP 0: Guard Clause - Ensure match is currently in progress
        if ("COMPLETED".equalsIgnoreCase(match.getStatus())) {
            throw new InvalidMatchStateException("Cannot record ball event. Match is already COMPLETED.");
        }


        int runsScored = request.getRunsScored();
        boolean isWicket = request.isWicket();
        boolean isExtra = request.isExtra();

        // STEP 1: Scoring Calculation
        int runsToAdd = runsScored;
        if (isExtra) {
            // Extras (e.g. Wide / No-Ball) add 1 penalty run + any runs ran off the bat/field
            runsToAdd += 1;
        }
        match.setTotalRuns(match.getTotalRuns() + runsToAdd);

        // STEP 2: Wicket Counter Update
        if (isWicket) {
            match.setTotalWickets(match.getTotalWickets() + 1);
        }

        // STEP 3: Valid Ball Count & Overs Formatting
        boolean isOverCompleted = false;
        if (!isExtra) {
            // Standard legal ball -> increment valid ball counter
            int currentValidBalls = match.getValidBalls() + 1;
            match.setValidBalls(currentValidBalls);

            // Compute completed overs and current ball index within over (0 to 5)
            int completedOvers = currentValidBalls / 6;
            int ballsInCurrentOver = currentValidBalls % 6;

            // Formats overs cleanly (e.g. 5 valid balls = 0.5, 6th ball resets fraction to 1.0)
            double formattedOvers = completedOvers + (ballsInCurrentOver / 10.0);
            match.setOversBowled(Math.round(formattedOvers * 10.0) / 10.0);

            // Over is complete when 6 legal balls have been delivered
            if (ballsInCurrentOver == 0) {
                isOverCompleted = true;
            }
        }

        // STEP 4: Strike Swap Logic
        // Rule A: Swap strike on odd runs (1, 3, 5)
        if (runsScored % 2 != 0) {
            swapStrike(match);
        }
        // Rule B: Swap strike at end of over (6 valid balls)
        if (isOverCompleted) {
            swapStrike(match);
        }

        // STEP 5: Match Completion Check
        int totalValidBallsLimit = match.getTotalOvers() * 6;
        if (match.getTotalWickets() >= 10 || match.getValidBalls() >= totalValidBallsLimit) {
            match.setStatus("COMPLETED");
        }

        // STEP 6: Append Ball Event to Embedded History List & Persist
        BallEvent ballEvent = new BallEvent(runsScored, isWicket, isExtra);
        match.getRecentBalls().add(ballEvent);

        return matchRepository.save(match);
    }

    /**
     * Helper method to swap strikerId and nonStrikerId in the Match entity.
     * 
     * @param match Active match object.
     */
    private void swapStrike(Match match) {
        String temp = match.getStrikerId();
        match.setStrikerId(match.getNonStrikerId());
        match.setNonStrikerId(temp);
    }
}

