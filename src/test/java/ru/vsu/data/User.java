package ru.vsu.data;

import ru.vsu.api.Entity;
import ru.vsu.api.Field;
import ru.vsu.api.Id;

@Entity(name = "user")
public class User {
    public User(Long id, Integer age, Float avgMark, String name, Boolean isAdmin) {
        this.id = id;
        this.age = age;
        this.avgMark = avgMark;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    @Id
    private Long id;
    private Integer age;
    @Field(name = "avg_mark")
    private Float avgMark;
    private String name;
    @Field(name = "is_admin")
    private Boolean isAdmin;
}
