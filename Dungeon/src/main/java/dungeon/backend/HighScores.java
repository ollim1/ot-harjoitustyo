/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.dao.DatabaseManager;
import dungeon.dao.RecordDao;
import dungeon.dao.domain.Person;
import dungeon.dao.domain.Record;
import dungeon.domain.Difficulty;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A backend class for handling scores.
 *
 * @author londes
 */
public class HighScores {

    private HashMap<Difficulty, List<Record>> tables;
    private static final int LIMIT = 10;
    private int charLimit = 10;
    private static final String[] DATABASE_INFORMATION = {"jdbc:h2:./savedData/HighScoresDatabase", "sa", ""};

    public HighScores() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.setup(DATABASE_INFORMATION[0], DATABASE_INFORMATION[1], DATABASE_INFORMATION[2]);
        databaseManager.createTablesIfAbsent();
        charLimit = databaseManager.getCharLimit();
        tables = listsByDifficulty();
    }

    /**
     * Produces a HashMap mapping difficulty levels to corresponding sets of
     * records. RecordDao handles sorting.
     *
     * @return a Difficulty->List map
     * @throws SQLException
     */
    private HashMap<Difficulty, List<Record>> listsByDifficulty() throws SQLException {
        RecordDao recordDao = RecordDao.getInstance();
        List fullList = recordDao.list();
        HashMap<Difficulty, List<Record>> map = new HashMap<>();
        for (Difficulty difficulty : Difficulty.values()) {
            map.put(difficulty, new ArrayList<>());
        }
        for (Object object : fullList) {
            Record record = (Record) object;
            map.get(record.getDifficulty()).add(record);
        }
        return map;
    }

    /**
     * Checks if the score is a high score in a specific difficulty class. All
     * scores are high scores until the size of the table hits 10.
     *
     * @param score the score
     * @param difficulty the difficulty class
     * @return
     */
    public boolean isHighScore(int score, Difficulty difficulty) {
        List<Record> table = tables.get(difficulty);
        if (table.size() < LIMIT) {
            return true;
        }
        return score > table.get(table.size() - 1).getScore();
    }

    /**
     * Adds a new high score. Does an isHighScore check just in case and shaves
     * off all scores after the ninth one in the table. The high score is first
     * inserted into the database, then fetched back into memory along with all
     * the other records.
     *
     * @param name
     * @param score
     * @param difficulty
     * @throws SQLException
     */
    public void addHighScore(String name, int score, Difficulty difficulty) throws SQLException {
        if (isHighScore(score, difficulty)) {
            RecordDao recordDao = RecordDao.getInstance();
            if (tables.get(difficulty).size() >= LIMIT) {
                List<Record> table = tables.get(difficulty);
                int length = table.size();
                for (int i = LIMIT - 1; i < length; i++) {
                    recordDao.delete(table.get(i).getId());
                }
            }
            recordDao.create(new Record(0, new Person(0, name), score, difficulty));
            tables = listsByDifficulty();
        }
    }

    public List<Record> getTable(Difficulty difficulty) {
        return tables.get(difficulty);
    }

    public static int getLIMIT() {
        return LIMIT;
    }

    public int getCharLimit() {
        return charLimit;
    }

}
