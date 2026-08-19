package com.cricket.scorecard.model;

/**
 * Value Object representing a single delivery (ball event) in a cricket match.
 * 
 * DEVELOPER NOTES:
 * - This object does not have its own @Id because it is stored as an embedded sub-document
 *   inside the 'recentBalls' list of the main Match document.
 */
public class BallEvent {

    // Number of runs scored off the bat or ran by batsmen on this delivery (e.g. 0, 1, 4, 6)
    private int runsScored;

    // Indicates whether a batsman was dismissed on this delivery
    private boolean isWicket;

    // Indicates whether this delivery was an extra (e.g., Wide or No-Ball)
    private boolean isExtra;

    /**
     * Default No-Args Constructor required for JSON deserialization by Jackson.
     */
    public BallEvent() {
    }

    /**
     * All-Args Constructor for creating a ball delivery record.
     * 
     * @param runsScored Runs scored on the delivery.
     * @param isWicket   Whether a wicket fell.
     * @param isExtra    Whether the delivery was an extra (Wide / No-Ball).
     */
    public BallEvent(int runsScored, boolean isWicket, boolean isExtra) {
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

