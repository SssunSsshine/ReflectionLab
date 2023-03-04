package ru.vsu.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static ru.vsu.utils.StringUtils.EMPTY_STRING;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Entity {

    String name() default EMPTY_STRING;

}
