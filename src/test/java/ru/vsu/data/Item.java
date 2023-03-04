package ru.vsu.data;

import ru.vsu.api.Entity;
import ru.vsu.api.Field;
import ru.vsu.api.Id;

import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    private Long id;
    private String name;
    @Field(name = "is_admin")
    private BigDecimal cost;

}
