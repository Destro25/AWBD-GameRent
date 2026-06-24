package com.awbd.gamerent;

import com.awbd.gamerent.model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ModelUnitTest {

    @Test
    void testGameModel() {
        Game game = new Game();
        game.setId(1L);
        game.setTitle("Test");
        game.setDescription("Desc");
        game.setDailyRentPrice(10.0);
        game.setStock(5);
        game.setCategories(new ArrayList<>());

        Console console = new Console();
        console.setName("PS5");
        game.setConsole(console);

        assertEquals(1L, game.getId());
        assertEquals("Test", game.getTitle());
        assertEquals("Desc", game.getDescription());
        assertEquals(10.0, game.getDailyRentPrice());
        assertEquals(5, game.getStock());
        assertNotNull(game.getCategories());
        assertEquals("PS5", game.getConsole().getName());
    }

    @Test
    void testUserModelAndProfile() {
        UserProfile profile = new UserProfile();
        profile.setId(1L);
        profile.setFirstName("Ion");
        profile.setLastName("Popescu");
        profile.setPhoneNumber("0700");
        profile.setAddress("Strada Test");

        User user = new User();
        user.setId(1L);
        user.setUsername("ion123");
        user.setEmail("ion@test.com");
        user.setPassword("pass");
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setUserProfile(profile);

        assertEquals(1L, user.getId());
        assertEquals("ion123", user.getUsername());
        assertEquals("ion@test.com", user.getEmail());
        assertEquals("pass", user.getPassword());
        assertEquals("ROLE_USER", user.getRole());
        assertEquals(true, user.isEnabled());

        assertNotNull(user.getUserProfile());
        assertEquals("Ion", user.getUserProfile().getFirstName());
        assertEquals("Popescu", user.getUserProfile().getLastName());
        assertEquals("0700", user.getUserProfile().getPhoneNumber());
        assertEquals("Strada Test", user.getUserProfile().getAddress());
    }

    @Test
    void testCategoryAndConsoleModel() {
        Category category = new Category("RPG");
        category.setId(1L);
        category.setGames(new ArrayList<>());

        assertEquals(1L, category.getId());
        assertEquals("RPG", category.getName());
        assertNotNull(category.getGames());

        Console console = new Console();
        console.setId(1L);
        console.setManufacturer("Sony");
        console.setGames(new ArrayList<>());

        assertEquals(1L, console.getId());
        assertEquals("Sony", console.getManufacturer());
        assertNotNull(console.getGames());
    }

    @Test
    void testRentalModel() {
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now().plusDays(2));
        rental.setActualReturnDate(LocalDate.now().plusDays(1));
        rental.setTotalPrice(20.0);

        assertEquals(1L, rental.getId());
        assertNotNull(rental.getRentalDate());
        assertNotNull(rental.getReturnDate());
        assertNotNull(rental.getActualReturnDate());
        assertEquals(20.0, rental.getTotalPrice());
    }
}