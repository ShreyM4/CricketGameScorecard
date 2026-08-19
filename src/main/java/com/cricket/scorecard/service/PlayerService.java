package com.cricket.scorecard.service;

import com.cricket.scorecard.dto.CreatePlayerRequest;
import com.cricket.scorecard.exception.ResourceNotFoundException;
import com.cricket.scorecard.model.Player;
import com.cricket.scorecard.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Layer component for managing Player domain operations.
 * 
 * DEVELOPER NOTES & ANNOTATIONS:
 * - @Service: Marks this class as a Spring Service component. Spring IoC container manages this class
 *   as a singleton bean holding core business operations for Player entities.
 */
@Service
public class PlayerService {

    // Final dependency field - guarantees immutability
    private final PlayerRepository playerRepository;

    /**
     * Constructor Injection:
     * DEVELOPER NOTE: In modern Spring Boot, Constructor Injection is preferred over field @Autowired.
     * It ensures the dependency is passed upon creation, enables final fields, and allows easy unit testing/mocking.
     * 
     * @param playerRepository Injected PlayerRepository bean.
     */
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Creates and persists a new Player entity in MongoDB.
     * Includes simple explicit Java validation checks for player parameters.
     * 
     * @param request CreatePlayerRequest DTO containing player details.
     * @return Saved Player document with generated MongoDB ID.
     */
    public Player createPlayer(CreatePlayerRequest request) {
        if (request == null || request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Player name is required");
        }
        if (request.getRole() == null || request.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Player role is required");
        }

        Player player = new Player(request.getName(), request.getRole());
        return playerRepository.save(player);
    }


    /**
     * Fetches a Player by unique MongoDB document ID.
     * 
     * DEVELOPER NOTE: Uses java.util.Optional returned by MongoRepository.
     * If player is absent, throws custom ResourceNotFoundException which maps to HTTP 404.
     * 
     * @param id Player document ID.
     * @return Found Player domain object.
     */
    public Player getPlayerById(String id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found with id: " + id));
    }

    /**
     * Retrieves all registered players from the database.
     * 
     * @return List of Player entities.
     */
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
}

