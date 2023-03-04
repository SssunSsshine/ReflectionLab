package ru.vsu.data;

import ru.vsu.api.Entity;
import ru.vsu.api.Id;

@Entity
public class Building {

    @Id
    private Long id;
    @Id
    private String anotherId;

}
