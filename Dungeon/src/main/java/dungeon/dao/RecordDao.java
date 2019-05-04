/*
 * @author londes
 */
package dungeon.dao;

import dungeon.dao.domain.Record;
import dungeon.dao.domain.Person;
import dungeon.domain.Difficulty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A DAO for the class Record. Implemented using a singleton design pattern with
 * lazy loading.
 *
 * @author londes
 */
public class RecordDao implements Dao<Record> {

    private class IntermediateResult {

        private int id;
        private int score;
        private int difficulty;
        private int person;

        public IntermediateResult(int id, int person, int score, int difficulty) {
            this.id = id;
            this.score = score;
            this.difficulty = difficulty;
            this.person = person;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public int getId() {
            return id;
        }

        public int getPerson() {
            return person;
        }

        public int getScore() {
            return score;
        }

    }
    private static RecordDao instance;

    private RecordDao() {
    }

    public static RecordDao getInstance() {
        if (instance == null) {
            instance = new RecordDao();
        }
        return instance;
    }

    @Override
    public List<Record> list() throws SQLException {
        PersonDao personDao = PersonDao.getInstance();
        List<Person> personList = personDao.list();
        HashMap<Integer, Person> people = new HashMap<>();
        for (Person person : personList) {
            people.put(person.getId(), person);
        }
        Connection conn = openConnection();
        PreparedStatement s = conn.prepareStatement("select * from Record order by score desc;");
        ResultSet results = s.executeQuery();
        List<IntermediateResult> intermediateList = readResults(results);
        conn.close();
        List<Record> records = parseRecords(intermediateList, people);

        return records;
    }

    private List<IntermediateResult> readResults(ResultSet results) throws SQLException {
        List<IntermediateResult> intermediateList = new ArrayList<>();
        while (results.next()) {
            intermediateList.add(new IntermediateResult(results.getInt("id"), results.getInt("personId"), results.getInt("score"), results.getInt("difficulty")));
        }
        return intermediateList;
    }

    private List<Record> parseRecords(List<IntermediateResult> intermediateList, HashMap<Integer, Person> people) throws SQLException {
        List<Record> records = new ArrayList<>();
        for (IntermediateResult result : intermediateList) {
            Difficulty difficulty = Difficulty.get(result.getDifficulty());
            Record record = new Record(result.getId(), people.get(result.getPerson()), result.getScore(), difficulty);
            records.add(record);
        }
        return records;
    }

    @Override
    public Record create(Record record) throws SQLException {
        PersonDao personDao = PersonDao.getInstance();
        Person person = personDao.create(record.getPerson());
        record.setPerson(person);
        Connection conn = openConnection();
        PreparedStatement s = conn.prepareStatement("insert into Record (id, personId, score, difficulty)"
                + " values (default, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        s.setInt(1, person.getId());
        s.setInt(2, record.getScore());
        s.setInt(3, record.getDifficulty().id);
        s.executeUpdate();
        ResultSet keys = s.getGeneratedKeys();
        setRecordId(keys, record);
        conn.close();
        return record;
    }

    private void setRecordId(ResultSet keys, Record record) throws SQLException {
        int id = -1;
        if (keys.next()) {
            id = keys.getInt(1);
        }
        record.setId(id);
    }

    private Connection openConnection() throws SQLException {
        return DatabaseManager.getInstance().openConnection();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = openConnection();
        PreparedStatement s = conn.prepareStatement("delete from Record where id = ?;");
        s.setInt(1, key);
        s.executeUpdate();
        conn.close();
    }

    @Override
    public Record read(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported for this class.");
    }
}
