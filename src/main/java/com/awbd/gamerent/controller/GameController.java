package com.awbd.gamerent.controller;

import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.service.CategoryService;
import com.awbd.gamerent.service.ConsoleService;
import com.awbd.gamerent.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final ConsoleService consoleService;
    private final CategoryService categoryService;

    public GameController(GameService gameService, ConsoleService consoleService, CategoryService categoryService) {
        this.gameService = gameService;
        this.consoleService = consoleService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String showGameList(Model model) {
        model.addAttribute("gamesList", gameService.findAllGames());
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
    public String saveGame(@ModelAttribute("game") Game game) {
        gameService.saveGame(game);
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