package com.cricket.scorecard.dto;

/**
 * Data Transfer Object (DTO) for client requests to start a new match.
 * 
 * DEVELOPER NOTES:
 * - Simple Java DTO holding incoming JSON parameters when initiating a match.
 */
public class CreateMatchRequest {

    // Name of batting team
    private String battingTeam;

    // Name of bowling team
    private String bowlingTeam;

    // Allocated overs limit
    private int totalOvers;


    // Optional initial striker player ID
    private String strikerId;

    // Optional initial non-striker player ID
    private String nonStrikerId;

    /**
     * Default No-Args Constructor required for JSON deserialization by Jackson.
     */
    public CreateMatchRequest() {
    }

    /**
     * Constructor without player IDs.
     */
    public CreateMatchRequest(String battingTeam, String bowlingTeam, int totalOvers) {
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.totalOvers = totalOvers;
    }

    /**
     * Full Constructor including opening batsmen IDs.
     */
    public CreateMatchRequest(String battingTeam, String bowlingTeam, int totalOvers, String strikerId, String nonStrikerId) {
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.totalOvers = totalOvers;
        this.strikerId = strikerId;
        this.nonStrikerId = nonStrikerId;
    }

    // --- Standard Getters & Setters ---

    public String getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }

    public String getBowlingTeam() {
        return bowlingTeam;
    }

    public void setBowlingTeam(String bowlingTeam) {
        this.bowlingTeam = bowlingTeam;
    }

    public int getTotalOvers() {
        return totalOvers;
    }

    public void setTotalOvers(int totalOvers) {
        this.totalOvers = totalOvers;
    }

    public String getStrikerId() {
        return strikerId;
    }

    public void setStrikerId(String strikerId) {
        this.strikerId = strikerId;
    }

    public String getNonStrikerId() {
        return nonStrikerId;
    }

    public void setNonStrikerId(String nonStrikerId) {
        this.nonStrikerId = nonStrikerId;
    }
}

