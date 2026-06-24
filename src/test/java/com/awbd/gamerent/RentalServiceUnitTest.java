package com.awbd.gamerent;

import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.model.Rental;
import com.awbd.gamerent.repository.GameRepository;
import com.awbd.gamerent.repository.RentalRepository;
import com.awbd.gamerent.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

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
        testRental.setTotalPrice(20.0);
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
    void testDeleteRental() {
        doNothing().when(rentalRepository).deleteById(1L);
        rentalService.deleteRental(1L);
        verify(rentalRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindPaginated() {
        Page<Rental> page = new PageImpl<>(Arrays.asList(testRental));
        when(rentalRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Rental> result = rentalService.findPaginated(1, 5, "rentalDate", "ASC");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testSaveRental_Success() {
        when(gameRepository.findById(10L)).thenReturn(Optional.of(testGame));
        when(gameRepository.save(any(Game.class))).thenReturn(testGame);
        when(rentalRepository.save(any(Rental.class))).thenReturn(testRental);

        Rental saved = rentalService.saveRental(testRental);

        assertNotNull(saved);
        assertEquals(4, testGame.getStock());
        assertEquals(20.0, saved.getTotalPrice());
    }

    @Test
    void testSaveRental_OutOfStock_ThrowsException() {
        testGame.setStock(0);
        when(gameRepository.findById(10L)).thenReturn(Optional.of(testGame));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            rentalService.saveRental(testRental);
        });

        assertEquals("Jocul nu mai este în stoc!", exception.getMessage());
        verify(rentalRepository, never()).save(any());
    }

    @Test
    void testProcessReturn_OnTime() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(testRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(testRental);

        Rental returnedRental = rentalService.processReturn(1L, testRental.getReturnDate());

        assertNotNull(returnedRental.getActualReturnDate());
        assertEquals(20.0, returnedRental.getTotalPrice());
        assertEquals(6, testGame.getStock());
    }

    @Test
    void testProcessReturn_LateWithPenalty() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(testRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(testRental);

        LocalDate lateDate = testRental.getReturnDate().plusDays(3);
        Rental returnedRental = rentalService.processReturn(1L, lateDate);

        assertNotNull(returnedRental.getActualReturnDate());
        assertEquals(80.0, returnedRental.getTotalPrice());
        assertEquals(6, testGame.getStock());
    }
}