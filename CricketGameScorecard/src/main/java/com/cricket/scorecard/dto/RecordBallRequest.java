package com.cricket.scorecard.dto;

/**
 * Data Transfer Object (DTO) payload for submitting a ball delivery event to a live match.
 * 
 * DEVELOPER NOTES:
 * - Passed to POST /api/matches/{id}/ball.
 * - Simple Java DTO holding ball delivery attributes.
 */
public class RecordBallRequest {

    // Number of runs scored on this ball
    private int runsScored;


    // True if a wicket fell on this delivery
    private boolean isWicket;

    // True if delivery was an extra (Wide or No-Ball)
    private boolean isExtra;

    /**
     * Default No-Args Constructor required for JSON deserialization by Jackson.
     */
    public RecordBallRequest() {
    }

    /**
     * All-Args Constructor for creating a delivery request.
     */
    public RecordBallRequest(int runsScored, boolean isWicket, boolean isExtra) {
        this.runsScored = runsScored;
        this.isWicket = isWicket;
        this.isExtra = isExtra;
    }

    // --- Standard Getters & Setters ---

    public int getRunsScored() {
        return runsScored;
    }

    public void setRunsScored(int runsScored) {
        this.runsScored = runsScored;
    }

    public boolean isWicket() {
        return isWicket;
    }

    public void setWicket(boolean wicket) {
        isWicket = wicket;
    }

    public boolean isExtra() {
        return isExtra;
    }

    public void setExtra(boolean extra) {
        isExtra = extra;
    }
}

