package com.awbd.gamerent.controller;

import com.awbd.gamerent.model.Console;
import com.awbd.gamerent.service.ConsoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consoles")
public class ConsoleController {

    private final ConsoleService consoleService;

    public ConsoleController(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    @GetMapping
    public String listConsoles(Model model) {
        model.addAttribute("consoles", consoleService.findAllConsoles());
        return "console-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("console", new Console());
        return "console-form";
    }

    @PostMapping("/save")
    public String saveConsole(@ModelAttribute("console") Console console) {
        consoleService.saveConsole(console);
        return "redirect:/consoles";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("console", consoleService.findConsoleById(id));
        return "console-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteConsole(@PathVariable Long id) {
        consoleService.deleteConsole(id);
        return "redirect:/consoles";
    }
}