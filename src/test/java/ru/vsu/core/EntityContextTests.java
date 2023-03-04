package ru.vsu.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.data.Game;
import ru.vsu.data.Item;
import ru.vsu.data.User;
import ru.vsu.exceptions.ContextException;
import ru.vsu.validator.ContextValidator;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 Этот тест нужно реализовывать ПОСЛЕ EntityContextValidatorTest
 */
public class EntityContextTests {

    private EntityContext entityContext;

    @BeforeEach
    public void beforeEach() {
        entityContext = new EntityContext(new ContextValidator());
    }

    @Test
    public void testSuccessfulLoadsContextForCorrectClasses() {
        // given
        Set<Class<?>> correctClasses = Set.of(User.class, Game.class);

        // then
        Assertions.assertDoesNotThrow(() -> entityContext.loadContext(correctClasses));
    }

    @Test
    public void testThrowsExceptionWhenLoadsIncorrectClasses() {
        // given
        Set<Class<?>> correctClasses = Set.of(User.class, Game.class, Item.class);

        // then
        Assertions.assertThrows(ContextException.class, () -> entityContext.loadContext(correctClasses));
    }

    @Test
    public void generatesCorrectMetadataForUser() {
        // given
        Set<Class<?>> correctClasses = Set.of(User.class);
        entityContext.loadContext(correctClasses);

        // then
        EntityMetaData metaData = entityContext.getEntityMetaData(User.class);

        // then
        assertEquals(metaData.getFields(), Arrays.stream(User.class.getDeclaredFields()).collect(Collectors.toList()));
        assertEquals(metaData.getSelectByQueryTemplate(), "SELECT id, age, avg_mark, name, is_admin FROM user WHERE id = %d");
        assertEquals(metaData.getInsertQueryTemplate(), "INSERT INTO user (id, age, avg_mark, name, is_admin) VALUES (%d, %d, %f, '%s', %b)");
    }

    @Test
    public void generatesCorrectMetadataForGame() {
        // given
        Set<Class<?>> correctClasses = Set.of(Game.class);
        entityContext.loadContext(correctClasses);

        // then
        EntityMetaData metaData = entityContext.getEntityMetaData(Game.class);

        // then
        assertEquals(metaData.getFields(), Arrays.stream(Game.class.getDeclaredFields()).collect(Collectors.toList()));
        assertEquals(metaData.getSelectByQueryTemplate(), "SELECT id, name FROM game WHERE id = %d");
        assertEquals(metaData.getInsertQueryTemplate(), "INSERT INTO game (id, name) VALUES (%d, '%s')");
    }

    @Test
    public void generatesCorrectQueriesForUser() {
        // given
        Set<Class<?>> correctClasses = Set.of(User.class);
        entityContext.loadContext(correctClasses);
        Repository<Long, User> repository = new Repository<>(entityContext, User.class);
        User user = new User(42L, 18, 4.32F, "Andrey", true);

        // then
        String selectQuery = repository.generateSelectQuery(42L);
        String updateQuery = repository.generateInsertQuery(user);

        // then
        assertEquals(selectQuery, "SELECT id, age, avg_mark, name, is_admin FROM user WHERE id = 42");
        assertEquals(updateQuery, "INSERT INTO user (id, age, avg_mark, name, is_admin) VALUES (42, 18, 4,320000, 'Andrey', true)");
    }

    @Test
    public void generatesCorrectQueriesForGame() {
        // given
        Set<Class<?>> correctClasses = Set.of(Game.class);
        entityContext.loadContext(correctClasses);
        Repository<Long, Game> repository = new Repository<>(entityContext, Game.class);
        Game game = new Game(12L, "Dark Souls");

        // then
        String selectQuery = repository.generateSelectQuery(42L);
        String updateQuery = repository.generateInsertQuery(game);

        // then
        assertEquals(selectQuery, "SELECT id, name FROM game WHERE id = 42");
        assertEquals(updateQuery, "INSERT INTO game (id, name) VALUES (12, 'Dark Souls')");
    }
}
