package com.cricket.scorecard.dto;

/**
 * Data Transfer Object (DTO) for handling client requests to create a new player.
 * 
 * DEVELOPER NOTES:
 * - Decouples HTTP request schema from internal database model (Player entity).
 * - Simple Java DTO holding incoming JSON payload fields.
 */
public class CreatePlayerRequest {

    // Player's full name
    private String name;

    // Player's role (e.g. "BATSMAN", "BOWLER")
    private String role;


    /**
     * Default No-Args Constructor required for JSON deserialization by Jackson.
     */
    public CreatePlayerRequest() {
    }

    /**
     * All-Args Constructor for instantiating request payloads.
     */
    public CreatePlayerRequest(String name, String role) {
        this.name = name;
        this.role = role;
    }

    // --- Standard Getters & Setters ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

