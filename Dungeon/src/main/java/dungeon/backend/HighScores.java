/*
 * @author olli m
 */
package dungeon.backend;

import dungeon.dao.DatabaseManager;
import dungeon.dao.RecordDao;
import dungeon.dao.domain.Person;
import dungeon.dao.domain.Record;
import dungeon.domain.Difficulty;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HighScores {

    private HashMap<Difficulty, List<Record>> tables;
    private static final int LIMIT = 10;
    private static final String[] DATABASE_INFORMATION = {"jdbc:h2:./savedData/HighScoresDatabase", "sa", ""};

    public HighScores() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.setup(DATABASE_INFORMATION[0], DATABASE_INFORMATION[1], DATABASE_INFORMATION[2]);
        Connection conn = databaseManager.openConnection();
        conn.prepareStatement("create table if not exists Person(id integer primary key auto_increment,"
                + " name varchar(10));").executeUpdate();
        conn.prepareStatement("create table if not exists Record(id integer primary key auto_increment,"
                + " personId integer, score integer, difficulty integer,"
                + " foreign key (personId) references Person(id));").executeUpdate();
        conn.close();
        tables = listsByDifficulty();
    }

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

    public boolean isHighScore(int score, Difficulty difficulty) {
        if (tables.get(difficulty).size() < LIMIT) {
            return true;
        }
        return score > tables.get(difficulty).get(LIMIT - 1).getScore();
    }

    public void addHighScore(String name, int score, Difficulty difficulty) throws SQLException {
        if (isHighScore(score, difficulty)) {
            RecordDao.getInstance().create(new Record(0, new Person(0, name), score, difficulty));
            tables = listsByDifficulty();
        }
    }

    public List<Record> getTable(Difficulty difficulty) {
        return tables.get(difficulty);
    }

    public static int getLIMIT() {
        return LIMIT;
    }

}
