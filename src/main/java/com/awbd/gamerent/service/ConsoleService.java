package com.awbd.gamerent.service;

import com.awbd.gamerent.model.Console;
import com.awbd.gamerent.repository.ConsoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsoleService {

    private final ConsoleRepository consoleRepository;

    public ConsoleService(ConsoleRepository consoleRepository) {
        this.consoleRepository = consoleRepository;
    }

    public Console saveConsole(Console console) {
        return consoleRepository.save(console);
    }

    public List<Console> findAllConsoles() {
        return consoleRepository.findAll();
    }

    public Console findConsoleById(Long id) {
        Optional<Console> consoleOptional = consoleRepository.findById(id);
        if (consoleOptional.isPresent()) {
            return consoleOptional.get();
        } else {
            throw new RuntimeException("Consola cu ID-ul " + id + " nu a fost gasita!");
        }
    }

    public void deleteConsole(Long id) {
        consoleRepository.deleteById(id);
    }
}