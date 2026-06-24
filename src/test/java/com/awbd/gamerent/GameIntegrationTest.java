package com.awbd.gamerent;

import com.awbd.gamerent.model.Console;
import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.repository.ConsoleRepository;
import com.awbd.gamerent.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ConsoleRepository consoleRepository;

    private Game testGame;
    private Console testConsole;

    @BeforeEach
    void setUp() {
        testConsole = new Console();
        testConsole.setName("PlayStation Test");
        testConsole.setManufacturer("Sony Test");
        consoleRepository.save(testConsole);

        testGame = new Game();
        testGame.setTitle("Joc de Test H2");
        testGame.setDailyRentPrice(15.5);
        testGame.setStock(5);
        testGame.setConsole(testConsole);

        gameRepository.save(testGame);
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
        consoleRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        Optional<Game> foundGame = gameRepository.findById(testGame.getId());

        assertTrue(foundGame.isPresent());
        assertEquals("Joc de Test H2", foundGame.get().getTitle());
        assertEquals("PlayStation Test", foundGame.get().getConsole().getName());
    }

    @Test
    void testUpdateGame() {
        testGame.setStock(10);
        gameRepository.save(testGame);

        Optional<Game> updatedGame = gameRepository.findById(testGame.getId());

        assertTrue(updatedGame.isPresent());
        assertEquals(10, updatedGame.get().getStock());
    }

    @Test
    void testDeleteGame() {
        gameRepository.deleteById(testGame.getId());

        Optional<Game> deletedGame = gameRepository.findById(testGame.getId());

        assertFalse(deletedGame.isPresent());
    }
}