package com.cricket.scorecard.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Domain entity model representing a Player stored in MongoDB.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @Document(collection = "players"): Marks this Java class as a MongoDB document entity.
 *   Spring Data MongoDB maps instances of this class to records inside the 'players' collection.
 */
@Document(collection = "players")
public class Player {

    /**
     * @Id: Marks this field as the primary key document identifier in MongoDB.
     * When saved without an explicit ID, MongoDB automatically generates a unique BSON ObjectId string (e.g., "66b8d8a7...").
     */
    @Id
    private String id;

    // Player's full name (e.g., "Virat Kohli")
    private String name;

    // Player's primary role in the team (e.g., "BATSMAN", "BOWLER", "ALL_ROUNDER")
    private String role;

    /**
     * Default No-Args Constructor.
     * DEVELOPER NOTE: Required by frameworks like Spring Data MongoDB and Jackson (for JSON deserialization).
     */
    public Player() {
    }

    /**
     * Full All-Args Constructor including document ID.
     * Useful for unit testing or when creating a player with a pre-determined ID.
     */
    public Player(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    /**
     * Business Constructor without ID.
     * Used when instantiating a new Player prior to saving to MongoDB.
     */
    public Player(String name, String role) {
        this.name = name;
        this.role = role;
    }

    // --- Standard Getters & Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

