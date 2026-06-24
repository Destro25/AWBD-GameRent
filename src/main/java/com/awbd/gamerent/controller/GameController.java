package com.awbd.gamerent.controller;

import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.service.CategoryService;
import com.awbd.gamerent.service.ConsoleService;
import com.awbd.gamerent.service.GameService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/games")
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    private final ConsoleService consoleService;
    private final CategoryService categoryService;

    public GameController(GameService gameService, ConsoleService consoleService, CategoryService categoryService) {
        this.gameService = gameService;
        this.consoleService = consoleService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showGames(Model model) {
        return findPaginated(1, "title", "asc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Game> page = gameService.findPaginated(pageNo, pageSize, sortField, sortDir);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("gamesList", page.getContent());
        return "game-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("consoles", consoleService.findAllConsoles());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "game-form";
    }


    @PostMapping("/save")
    public String saveGame(@Valid @ModelAttribute("game") Game game, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("consoles", consoleService.findAllConsoles());
            model.addAttribute("categories", categoryService.findAllCategories());
            return "game-form";
        }

        gameService.saveGame(game);
        logger.info("Eveniment: Un joc nou a fost adăugat sau editat în baza de date cu titlul: {}", game.getTitle());
        return "redirect:/games";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("game", gameService.findGameById(id));
        model.addAttribute("consoles", consoleService.findAllConsoles());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "game-form";
    }


    @GetMapping("/delete/{id}")
    public String deleteGame(@PathVariable("id") Long id) {
        gameService.deleteGame(id);
        return "redirect:/games";
    }
}