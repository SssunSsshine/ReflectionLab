package ru.vsu.core;

import java.awt.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Repository<ID, Entity> {

    private final EntityMetaData entityMetaData;

    public Repository(EntityContext entityContextLoader, Class<Entity> typeClass) {
        this.entityMetaData = entityContextLoader.getEntityMetaData(typeClass);
    }

    public String generateSelectQuery(ID id) {
        return String.format(entityMetaData.getSelectByQueryTemplate(), id);
    }

    public String generateInsertQuery(Entity entity)  {
        ArrayList<Object> values = new ArrayList<>();
        for (Field field: entity.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                values.add(field.get(entity));
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return String.format(entityMetaData.getInsertQueryTemplate(), values.toArray());
    }

}
