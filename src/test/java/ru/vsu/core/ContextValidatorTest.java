package ru.vsu.core;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vsu.data.Building;
import ru.vsu.data.Item;
import ru.vsu.data.Student;
import ru.vsu.data.User;
import ru.vsu.exceptions.ContextException;
import ru.vsu.validator.ContextValidator;

import java.util.Set;

/*
 Этот тест нужно реализовывать ПЕРВЫМ!
 */

import static ru.vsu.configuration.DefaultConfiguration.DEFAULT_SUPPORTED_TYPES;

public class ContextValidatorTest {

    private final ContextValidator entityContextValidator = new ContextValidator();

    @Test
    @DisplayName("Валидация успешна для классов, почеменных аннотацией @Entity")
    public void doesNotThrowExceptionForClassesAnnotatedWithEntity() {
        // given
        Set<Class<?>> classesAnnotatedWithEntity = Set.of(User.class);

        // then
        Assertions.assertDoesNotThrow(() -> entityContextValidator.validateIsAnnotatedWithEntity(classesAnnotatedWithEntity));
    }

    @Test
    @DisplayName("Валидация выбрасывает ошибку для классов, не помеченных аннотацией @Entity")
    public void doesThrowExceptionForClassesThanNotAnnotatedWithEntity() {
        // given
        Set<Class<?>> classesAnnotatedWithEntity = Set.of(Student.class);

        // then
        Assertions.assertThrows(ContextException.class, () -> entityContextValidator.validateIsAnnotatedWithEntity(classesAnnotatedWithEntity));
    }

    @Test
    @DisplayName("Валидация успешна, если переданные Entity содержат только допустимые типы полей")
    public void doesNotThrowExceptionForEntityWithOnlySupportedTypes() {
        // given
        Set<Class<?>> supportedTypes = DEFAULT_SUPPORTED_TYPES;
        ContextValidator validator = new ContextValidator();

        // then
        Assertions.assertDoesNotThrow(() -> validator.validateHasOnlySupportedTypes(supportedTypes, Set.of(Student.class, User.class)));
    }

    @Test
    @DisplayName("Выбрасываетс ошибка, если переданные Entity содержат недопустимые типы полей")
    public void throwExceptionForEntityWithUnsupportedTypes() {
        // given
        Set<Class<?>> supportedTypes = DEFAULT_SUPPORTED_TYPES;
        ContextValidator validator = new ContextValidator();

        // then
        Assertions.assertThrows(ContextException.class, () -> validator.validateHasOnlySupportedTypes(supportedTypes, Set.of(Item.class)));
    }

    @Test
    @DisplayName("Валидация успешна, если переданные Entity содержат одно поле, помеченное анотацией id")
    public void doesNotThrowExceptionForEntityWithOnlySOneIdField() {
        // given
        ContextValidator validator = new ContextValidator();

        // then
        Assertions.assertDoesNotThrow(() -> validator.validateHasId(Set.of(Item.class, User.class)));
    }

    @Test
    @DisplayName("Выброшенно исключеие, если переданные Entity не содержат айди или содежат больше одного айди")
    public void throwExceptionWhenNotCorrectIdsCount() {
        // given
        ContextValidator validator = new ContextValidator();

        // then
        Assertions.assertThrows(ContextException.class, () -> validator.validateHasId(Set.of(Building.class)));
        Assertions.assertThrows(ContextException.class, () -> validator.validateHasId(Set.of(Student.class)));
    }

}
