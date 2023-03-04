package ru.vsu.core;

import java.lang.reflect.Field;
import java.util.List;

public class EntityMetaData {

    private final String selectByQueryTemplate;
    private final String insertQueryTemplate;
    private final String tableName;
    private final List<Field> fields;

    public EntityMetaData(String selectByQueryTemplate, String insertQueryTemplate, String tableName, List<Field> fields) {
        this.selectByQueryTemplate = selectByQueryTemplate;
        this.insertQueryTemplate = insertQueryTemplate;
        this.tableName = tableName;
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    public String getSelectByQueryTemplate() {
        return selectByQueryTemplate;
    }

    public String getInsertQueryTemplate() {
        return insertQueryTemplate;
    }

    public String getTableName() {
        return tableName;
    }
}
