package com.awbd.gamerent.service;

import com.awbd.gamerent.exception.ResourceNotFoundException;
import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.repository.GameRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    public Page<Game> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        return gameRepository.findAll(pageable);
    }
}