package com.awbd.gamerent.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        logger.error("Atenție! S-a produs o eroare critică în aplicație: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        logger.error("Atenție! S-a produs o eroare critică în aplicație: {}", ex.getMessage(), ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/500";
    }
}