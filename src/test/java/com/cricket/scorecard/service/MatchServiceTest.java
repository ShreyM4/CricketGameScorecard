package com.cricket.scorecard.service;

import com.cricket.scorecard.dto.CreateMatchRequest;
import com.cricket.scorecard.dto.RecordBallRequest;
import com.cricket.scorecard.exception.InvalidMatchStateException;
import com.cricket.scorecard.model.Match;
import com.cricket.scorecard.repository.MatchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    private Match sampleMatch;

    @BeforeEach
    void setUp() {
        sampleMatch = new Match("match-1", "IND", "AUS", 1, "player-1", "player-2");
        sampleMatch.setStatus("LIVE");
    }

    @Test
    @DisplayName("Should create match successfully with initial state")
    void testCreateMatch() {
        CreateMatchRequest request = new CreateMatchRequest("IND", "AUS", 20, "p1", "p2");
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Match match = matchService.createMatch(request);

        assertNotNull(match);
        assertEquals("IND", match.getBattingTeam());
        assertEquals("AUS", match.getBowlingTeam());
        assertEquals(20, match.getTotalOvers());
        assertEquals("LIVE", match.getStatus());
        assertEquals(0, match.getTotalRuns());
        assertEquals(0, match.getTotalWickets());
        assertEquals(0.0, match.getOversBowled());
    }

    @Test
    @DisplayName("Should update runs, overs, and swap strike on single run")
    void testRecordBall_SingleRun() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(sampleMatch));
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RecordBallRequest ball = new RecordBallRequest(1, false, false);
        Match updated = matchService.recordBall("match-1", ball);

        assertEquals(1, updated.getTotalRuns());
        assertEquals(0, updated.getTotalWickets());
        assertEquals(0.1, updated.getOversBowled());
        assertEquals(1, updated.getValidBalls());
        assertEquals("player-2", updated.getStrikerId());
        assertEquals("player-1", updated.getNonStrikerId());
    }

    @Test
    @DisplayName("Should add extra run without increasing valid balls or overs")
    void testRecordBall_Extra() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(sampleMatch));
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RecordBallRequest ball = new RecordBallRequest(0, false, true); // Wide
        Match updated = matchService.recordBall("match-1", ball);

        assertEquals(1, updated.getTotalRuns()); // 1 run extra
        assertEquals(0, updated.getValidBalls());
        assertEquals(0.0, updated.getOversBowled());
        assertEquals("player-1", updated.getStrikerId()); // No odd runs scored, strike stays
    }

    @Test
    @DisplayName("Should swap strike at end of over (6 valid balls)")
    void testRecordBall_OverCompletion() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(sampleMatch));
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 5 dot balls (even runs, no strike swap)
        for (int i = 0; i < 5; i++) {
            matchService.recordBall("match-1", new RecordBallRequest(0, false, false));
        }

        assertEquals(0.5, sampleMatch.getOversBowled());
        assertEquals("player-1", sampleMatch.getStrikerId());

        // 6th ball - dot ball
        Match endOfOver = matchService.recordBall("match-1", new RecordBallRequest(0, false, false));

        assertEquals(1.0, endOfOver.getOversBowled());
        assertEquals("player-2", endOfOver.getStrikerId()); // Strike swapped on over end
    }

    @Test
    @DisplayName("Should mark match as COMPLETED when 10 wickets fall")
    void testMatchCompletion_Wickets() {
        sampleMatch.setTotalOvers(20);
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(sampleMatch));
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

        for (int i = 0; i < 9; i++) {
            matchService.recordBall("match-1", new RecordBallRequest(0, true, false));
        }
        assertEquals("LIVE", sampleMatch.getStatus());

        Match finalWicket = matchService.recordBall("match-1", new RecordBallRequest(0, true, false));
        assertEquals(10, finalWicket.getTotalWickets());
        assertEquals("COMPLETED", finalWicket.getStatus());
    }

    @Test
    @DisplayName("Should mark match as COMPLETED when total overs bowled")
    void testMatchCompletion_Overs() {
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(sampleMatch)); // 1 over match
        when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

        for (int i = 0; i < 5; i++) {
            matchService.recordBall("match-1", new RecordBallRequest(0, false, false));
        }
        assertEquals("LIVE", sampleMatch.getStatus());

        Match lastBall = matchService.recordBall("match-1", new RecordBallRequest(0, false, false));
        assertEquals(1.0, lastBall.getOversBowled());
        assertEquals("COMPLETED", lastBall.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when attempting to record ball on completed match")
    void testRecordBall_CompletedMatchException() {
        sampleMatch.setStatus("COMPLETED");
        when(matchRepository.findById("match-1")).thenReturn(Optional.of(sampleMatch));

        RecordBallRequest ball = new RecordBallRequest(1, false, false);

        assertThrows(InvalidMatchStateException.class, () -> matchService.recordBall("match-1", ball));
    }
}
