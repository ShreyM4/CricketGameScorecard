package com.cricket.scorecard.repository;

import com.cricket.scorecard.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB Repository interface for Player document entities.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @Repository: Indicates that this interface is a Spring Data Access Component.
 * - MongoRepository<Player, String>: By extending MongoRepository, Spring Data automatically generates
 *   a dynamic proxy implementation at runtime. It provides built-in CRUD operations out of the box:
 *   - save(Player entity) -> Inserts or updates player in 'players' collection
 *   - findById(String id) -> Retrieves player by ID
 *   - findAll()           -> Retrieves all players
 *   - deleteById(String id) -> Deletes player by ID
 * 
 * Generics Breakdown:
 * - Player: The document entity type.
 * - String: The type of the document primary key (@Id).
 */
@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
}

