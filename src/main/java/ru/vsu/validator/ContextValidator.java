package ru.vsu.validator;

import ru.vsu.api.Entity;
import ru.vsu.api.Id;
import ru.vsu.exceptions.ContextException;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class ContextValidator {

    public void validateIsAnnotatedWithEntity(Set<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if (clazz.getDeclaredAnnotation(Entity.class) == null) {
                throw new ContextException("Class not annotated with entity");
            }
        }
    }


    public void validateHasOnlySupportedTypes(Set<Class<?>> validTypes, Set<Class<?>> entities) {
        for (Class<?> entity : entities) {
            if (!(Arrays.stream(entity.getDeclaredFields()).allMatch(field -> validTypes.contains(field.getType())))) {
                throw new ContextException("Entity has not only supported types");
            }
        }
    }

    public void validateHasId(Set<Class<?>> entities) {
        for (Class<?> entity : entities) {
            long idsCount = Arrays.stream(entity.getDeclaredFields())
                    .map(it->it.getDeclaredAnnotation(Id.class))
                    .filter(Objects::nonNull).count();
            if (idsCount != 1) {
                throw new ContextException("Entity has not id or has more than 1");
            }
        }
    }
}
