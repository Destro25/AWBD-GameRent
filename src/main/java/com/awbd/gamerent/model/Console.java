package com.awbd.gamerent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "consoles")
public class Console {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Numele platformei este obligatoriu!")
    @Size(min = 2, max = 50, message = "Numele trebuie să aibă între 2 și 50 de caractere!")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Producătorul nu poate lipsi!")
    private String manufacturer;

    @OneToMany(mappedBy = "console", cascade = CascadeType.ALL)
    private List<Game> games;

    public Console() {
    }

    public Console(String name, String manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public List<Game> getGames() { return games; }
    public void setGames(List<Game> games) { this.games = games; }
}