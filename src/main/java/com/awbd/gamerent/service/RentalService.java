package com.awbd.gamerent.service;

import com.awbd.gamerent.model.Game;
import com.awbd.gamerent.model.Rental;
import com.awbd.gamerent.repository.GameRepository;
import com.awbd.gamerent.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final GameRepository gameRepository;

    public RentalService(RentalRepository rentalRepository, GameRepository gameRepository) {
        this.rentalRepository = rentalRepository;
        this.gameRepository = gameRepository;
    }


    public Rental saveRental(Rental rental) {


        if (rental.getGame() != null && rental.getGame().getId() != null
                && rental.getRentalDate() != null && rental.getReturnDate() != null) {


            Optional<Game> gameOptional = gameRepository.findById(rental.getGame().getId());

            if (gameOptional.isPresent()) {
                Game game = gameOptional.get();

                if (game.getStock() != null && game.getStock() > 0) {
                    game.setStock(game.getStock() - 1);
                    gameRepository.save(game);
                    long days = ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate());

                    if (days <= 0) {
                        days = 1;
                    }

                    double calculatedPrice = days * game.getDailyRentPrice();
                    rental.setTotalPrice(calculatedPrice);
                }else {
                    throw new RuntimeException("Jocul nu mai este în stoc!");
                }

            }
        }

        return rentalRepository.save(rental);
    }

    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental findRentalById(Long id) {
        Optional<Rental> rentalOptional = rentalRepository.findById(id);
        if (rentalOptional.isPresent()) {
            return rentalOptional.get();
        } else {
            throw new RuntimeException("Rezervarea cu ID-ul " + id + " nu a fost gasita!");
        }
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    public Rental processReturn(Long rentalId, LocalDate returnDate) {
        Rental rental = findRentalById(rentalId);
        rental.setActualReturnDate(returnDate);

        Game game = rental.getGame();
        game.setStock(game.getStock() + 1);
        gameRepository.save(game);

        if (returnDate.isAfter(rental.getReturnDate())) {
            long lateDays = ChronoUnit.DAYS.between(rental.getReturnDate(), returnDate);

            double penalty = lateDays * (game.getDailyRentPrice() * 2);

            rental.setTotalPrice(rental.getTotalPrice() + penalty);
        }

        return rentalRepository.save(rental);
    }
}