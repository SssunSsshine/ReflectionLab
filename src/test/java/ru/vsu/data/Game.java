package ru.vsu.data;

import ru.vsu.api.Entity;
import ru.vsu.api.Id;

@Entity
public class Game {

    @Id
    private Long id;
    private String name;

    public Game(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
