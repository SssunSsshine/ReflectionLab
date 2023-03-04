package ru.vsu.core;

import ru.vsu.api.Id;
import ru.vsu.validator.ContextValidator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static ru.vsu.configuration.DefaultConfiguration.DEFAULT_SUPPORTED_TYPES;

public class EntityContext {
    private final ContextValidator contextValidator;
    private final Map<Class<?>, EntityMetaData> classNameToQueryMetadata = new HashMap<>();

    public EntityContext(ContextValidator contextValidator) {
        this.contextValidator = contextValidator;
    }

    public void loadContext(Set<Class<?>> classes) {
        contextValidator.validateIsAnnotatedWithEntity(classes);
        contextValidator.validateHasOnlySupportedTypes(DEFAULT_SUPPORTED_TYPES, classes);
        contextValidator.validateHasId(classes);

        for (Class<?> clazz : classes) {
            List<String> fieldName = Arrays.stream(clazz.getDeclaredFields())
                    .map(field -> Optional.ofNullable(field.getDeclaredAnnotation(ru.vsu.api.Field.class))
                    .map(ru.vsu.api.Field::name).orElse(field.getName()))
                    .collect(Collectors.toList());
            String fieldNames = String.join(", ", fieldName);
            Field id = null;
            Map<String, String> type = getTypeMap();
            List<String> formTypes = new ArrayList<>();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.getDeclaredAnnotation(Id.class) != null) {
                    id = field;
                }
                formTypes.add(type.get(field.getType().getName()));
            }

            String select = String.format("SELECT %s FROM %s WHERE %s = %s", fieldNames, clazz.getSimpleName().toLowerCase(),
                    id.getName().toLowerCase(), type.get(id.getType().getName()));
            String insert = String.format("INSERT INTO %s (%s) VALUES (%s)", clazz.getSimpleName().toLowerCase(),
                    fieldNames, String.join(", ",formTypes));
            EntityMetaData entityMetaData = new EntityMetaData(select, insert,
                    clazz.getSimpleName(), Arrays.asList(clazz.getDeclaredFields()));
            classNameToQueryMetadata.put(clazz, entityMetaData);
        }
    }

    private static Map<String, String> getTypeMap() {
        Map<String,String> type = new HashMap<>();
        type.put(Integer.class.getName(),"%d");
        type.put(Long.class.getName(),"%d");
        type.put(Double.class.getName(),"%f");
        type.put(Float.class.getName(),"%f");
        type.put(String.class.getName(),"'%s'");
        type.put(Boolean.class.getName(),"%b");
        return type;
    }

    public EntityMetaData getEntityMetaData(Class<?> entityType) {
        return classNameToQueryMetadata.get(entityType);
    }

}
