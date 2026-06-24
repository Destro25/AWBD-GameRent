package com.awbd.gamerent;

import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.model.Rental;
import com.awbd.gamerent.repository.RentalRepository;
import com.awbd.gamerent.repository.GameRepository;
import com.awbd.gamerent.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceUnitTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private RentalService rentalService;

    private Rental testRental;
    private Game testGame;

    @BeforeEach
    void setUp() {
        testGame = new Game();
        testGame.setId(10L);
        testGame.setStock(5);
        testGame.setDailyRentPrice(10.0);

        testRental = new Rental();
        testRental.setId(1L);
        testRental.setGame(testGame);
        testRental.setRentalDate(LocalDate.now());
        testRental.setReturnDate(LocalDate.now().plusDays(2));
    }

    @Test
    void testFindAllRentals() {
        when(rentalRepository.findAll()).thenReturn(Arrays.asList(testRental));

        List<Rental> rentals = rentalService.findAllRentals();

        assertEquals(1, rentals.size());
    }

    @Test
    void testFindRentalById() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(testRental));

        Rental found = rentalService.findRentalById(1L);

        assertNotNull(found);
    }

    @Test
    void testSaveRental() {
        when(rentalRepository.save(any(Rental.class))).thenReturn(testRental);
        lenient().when(gameRepository.save(any(Game.class))).thenReturn(testGame);

        Rental saved = rentalService.saveRental(testRental);

        assertNotNull(saved);
        verify(rentalRepository, times(1)).save(testRental);
    }
}