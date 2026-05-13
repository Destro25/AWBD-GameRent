package com.awbd.gamerent.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private Double dailyRentPrice;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "console_id")
    private Console console;

    @ManyToMany
    @JoinTable(
            name = "game_categories",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Rental> rentals;

    public Game() {
    }

    public Game(String title, String description, Double dailyRentPrice, Integer stock) {
        this.title = title;
        this.description = description;
        this.dailyRentPrice = dailyRentPrice;
        this.stock = stock;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDailyRentPrice() {
        return dailyRentPrice;
    }

    public void setDailyRentPrice(Double dailyRentPrice) {
        this.dailyRentPrice = dailyRentPrice;
    }

    public Console getConsole() { return console; }
    public void setConsole(Console console) { this.console = console; }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}