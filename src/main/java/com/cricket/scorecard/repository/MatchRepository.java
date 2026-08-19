package com.cricket.scorecard.repository;

import com.cricket.scorecard.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB Repository interface for Match document entities.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @Repository: Marks this interface as a Data Access Component in the Spring context.
 * - MongoRepository<Match, String>: Automatically provides CRUD operations for Match documents:
 *   - save(Match match)       -> Persists match state (initial creation or updated live scores)
 *   - findById(String matchId)-> Fetches match document by ID
 *   - findAll()               -> Returns all match records
 */
@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
}

