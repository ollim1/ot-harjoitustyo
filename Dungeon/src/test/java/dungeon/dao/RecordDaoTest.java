/*
 * @author londes
 */
package dungeon.dao;

import dungeon.dao.domain.Person;
import dungeon.dao.domain.Record;
import dungeon.domain.Difficulty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author londes
 */
public class RecordDaoTest {

    private DatabaseManager manager;

    public RecordDaoTest() {
    }

    @Before
    public void setUp() throws SQLException {
        manager = DatabaseManager.getInstance();
        manager.setup("jdbc:h2:./savedData/TestDatabase", "sa", "");
        Connection conn = manager.openConnection();
        conn.prepareStatement("drop table if exists Person;").executeUpdate();
        conn.prepareStatement("drop table if exists Record;").executeUpdate();
        conn.prepareStatement("create table if not exists Person(id integer primary key auto_increment,"
                + " name varchar(10));").executeUpdate();
        conn.prepareStatement("create table if not exists Record(id integer primary key auto_increment,"
                + " personId integer, score integer, difficulty integer,"
                + " foreign key (personId) references Person(id));").executeUpdate();
        conn.close();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreation() {
        try {
            RecordDao recordDao = RecordDao.getInstance();
            recordDao.create(new Record(0, new Person(0, "Matti"), 1000, Difficulty.EASY));
            recordDao.create(new Record(0, new Person(0, "Matti"), 2000, Difficulty.EASY));
            Connection conn = manager.openConnection();
            PreparedStatement s = conn.prepareStatement("select * from Record order by score desc");
            ResultSet rs = s.executeQuery();
            ArrayList<int[]> results = new ArrayList<>();
            while (rs.next()) {
                results.add(new int[]{rs.getInt("id"), rs.getInt("personId"),
                    rs.getInt("score"), rs.getInt("difficulty")});
            }
            conn.close();
            if (results.size() != 2) {
                if (results.size() == 1) {
                    fail("The table has one line when it should have two");
                }
                fail("The table has " + results.size() + " lines when it should have 2");
            }
            for (int[] result : results) {
                if (result[1] != 1) {
                    fail("The record has the wrong personId: expected 1, but got " + result[1]);
                }
            }
            for (int[] result : results) {
                if (result[3] != 1) {
                    fail("The records have the wrong difficulty: expected 1, but got " + result[1]);
                }
            }
        } catch (SQLException e) {
            fail("Got an SQL exception: " + e.getMessage());
        }
    }

    @Test
    public void testListing() {
        try {
            Connection conn = manager.openConnection();
            conn.prepareStatement("insert into Person (id, name)"
                    + " values (default, 'Matti');").executeUpdate();
            conn.prepareStatement("insert into Record (id, personId, score, difficulty)"
                    + " values (default, 1, 2000, 3);").executeUpdate();
            conn.prepareStatement("insert into Record (id, personId, score, difficulty)"
                    + " values (default, 1, 1000, 3);").executeUpdate();
            conn.prepareStatement("insert into Record (id, personId, score, difficulty)"
                    + " values (default, 1, 3000, 3);").executeUpdate();
            conn.close();
            RecordDao recordDao = RecordDao.getInstance();
            List<Record> list = recordDao.list();
            if (list == null) {
                fail("The DAO did not return a List");
            }
            if (list.size() != 3) {
                fail("The list that was returned did not have 3 items");
            }
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i - 1).getScore() < list.get(i).getScore()) {
                    fail("The list is not sorted in descending order by score");
                }
            }
            for (Record record : list) {
                if (record.getDifficulty() != Difficulty.HARD) {
                    fail("The entries in the list do not have the correct difficulty setting: expected "
                            + Difficulty.HARD + ", but got " + record.getDifficulty());
                }
                if (record.getPerson() == null) {
                    fail("An entry in the list does not reference a person");
                }
                if (record.getPerson().getName() == null) {
                    fail("A person referenced in the list has no name");
                }
                if (!record.getPerson().getName().equals("Matti")) {
                    fail("A person referenced in the list has the wrong name: expected 'Matti', but got "
                            + record.getPerson().getName());
                }
            }
        } catch (SQLException e) {
            fail("Got an SQL exception: " + e.getMessage());
        }
    }

}
