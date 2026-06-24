package com.awbd.gamerent.service;

import com.awbd.gamerent.exception.ResourceNotFoundException;
import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }


    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }


    public Game findGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jocul cu ID-ul " + id + " nu a fost găsit în baza de date!"));
    }


    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}