/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.dao.DatabaseManager;
import dungeon.dao.domain.Person;
import dungeon.dao.domain.Record;
import dungeon.domain.Difficulty;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class HighScoresTest {

    DatabaseManager manager;
    HighScores highScores;

    public HighScoresTest() {
    }

    @Before
    public void setUp() throws SQLException {
        manager = DatabaseManager.getInstance();
        manager.setup("jdbc:h2:./savedData/TestDatabase", "sa", "");
        Connection conn = manager.openConnection();
        conn.prepareStatement("drop table if exists Record;").executeUpdate();
        conn.prepareStatement("drop table if exists Person;").executeUpdate();
        conn.close();
        manager.createTablesIfAbsent();
        highScores = new HighScores();
    }

    @After
    public void tearDown() throws SQLException {
        Connection conn = manager.openConnection();
        conn.prepareStatement("drop table if exists Record;").executeUpdate();
        conn.prepareStatement("drop table if exists Person;").executeUpdate();
        conn.close();
    }

    @Test
    public void addHighScoreAddsHighScore() {
        try {
            Connection conn = manager.openConnection();
            conn.prepareStatement("delete from Record;").executeUpdate();
            conn.prepareStatement("delete from Person;").executeUpdate();
            conn.close();
            highScores.addHighScore("Matti", 1000, Difficulty.HARD);
            if (highScores.getTable(Difficulty.HARD).isEmpty()) {
                fail("The table returned did not contain the inserted high score");
            }
            Record record = highScores.getTable(Difficulty.HARD).get(0);
            Person person = record.getPerson();
            if (person == null) {
                fail("The record did not have a person associated with it");
            }
            if (person.getName() == null) {
                fail("The person associated with the record did not have a name");
            }
            if (!person.getName().equals("Matti")) {
                fail("The person associated with the record did not have the correct name: expected 'Matti', but got " + person.getName());
            }
            if (record.getDifficulty() != Difficulty.HARD) {
                fail("The record had the wrong difficulty: expected Difficulty.HARD, but got " + record.getDifficulty());
            }
            if (record.getScore() != 1000) {
                fail("The record did not have the correct score: expected 1000, but got " + record.getScore());
            }
        } catch (SQLException e) {
            fail("Got an SQLException: " + e.getMessage());
        }
    }

    @Test
    public void highScoreTableLimit() {
        try {
            Connection conn = manager.openConnection();
            conn.prepareStatement("delete from Record;").executeUpdate();
            conn.prepareStatement("delete from Person;").executeUpdate();
            conn.close();
            int limit = highScores.getLIMIT();
            for (int i = 0; i < limit; i++) {
                highScores.addHighScore("Matti", 9001 + i, Difficulty.HARD);
            }
            highScores.addHighScore("Matti", 1000, Difficulty.HARD);
            List<Record> table = highScores.getTable(Difficulty.HARD);
            if (table.size() > 10) {
                fail("The table grew past size 10");
            }
            int expected = 9001;
            if (table.get(limit - 1).getScore() != expected) {
                fail("The table had the wrong record in the last place: expected "
                        + expected + ", but got " + table.get(limit - 1).getScore());
            }
        } catch (SQLException e) {
            fail("Got an SQLException: " + e.getMessage());
        }
    }
}
