package com.awbd.gamerent.controller;

import com.awbd.gamerent.model.Rental;
import com.awbd.gamerent.service.GameService;
import com.awbd.gamerent.service.RentalService;
import com.awbd.gamerent.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;
    private final GameService gameService;

    public RentalController(RentalService rentalService, UserService userService, GameService gameService) {
        this.rentalService = rentalService;
        this.userService = userService;
        this.gameService = gameService;
    }

    @GetMapping
    public String showRentalsList(Model model) {
        return findPaginated(1, "rentalDate", "desc", model);
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;
        Page<Rental> page = rentalService.findPaginated(pageNo, pageSize, sortField, sortDir);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("rentals", page.getContent());
        return "rental-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("rental", new Rental());
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("games", gameService.findAllGames());
        return "rental-form";
    }

    @PostMapping("/save")
    public String saveRental(@Valid @ModelAttribute("rental") Rental rental, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAllUsers());
            model.addAttribute("games", gameService.findAllGames());
            return "rental-form";
        }
        rentalService.saveRental(rental);
        return "redirect:/rentals";
    }

    @GetMapping("/delete/{id}")
    public String deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return "redirect:/rentals";
    }

    @GetMapping("/return/{id}")
    public String returnGame(@PathVariable Long id) {
        rentalService.processReturn(id, LocalDate.now());

        return "redirect:/rentals";
    }
}