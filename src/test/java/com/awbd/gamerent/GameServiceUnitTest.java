package com.awbd.gamerent;

import com.awbd.gamerent.exception.ResourceNotFoundException;
import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.repository.GameRepository;
import com.awbd.gamerent.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceUnitTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private Game testGame;

    @BeforeEach
    void setUp() {
        testGame = new Game();
        testGame.setId(99L);
        testGame.setTitle("Joc Mockito");
        testGame.setStock(5);
    }

    @Test
    void testSaveGame() {
        when(gameRepository.save(any(Game.class))).thenReturn(testGame);

        Game savedGame = gameService.saveGame(testGame);

        assertNotNull(savedGame);
        assertEquals("Joc Mockito", savedGame.getTitle());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void testFindGameById_ThrowsException() {
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            gameService.findGameById(1L);
        });

        assertTrue(exception.getMessage().contains("nu a fost găsit"));
    }
}