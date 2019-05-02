/*
 * @author londes
 */
package dungeon.dao;

import dungeon.dao.domain.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author londes
 */
public class PersonDaoTest {

    private DatabaseManager manager;

    public PersonDaoTest() {
    }

    @Before
    public void setUp() throws SQLException {
        manager = DatabaseManager.getInstance();
        manager.setup("jdbc:h2:./savedData/TestDatabase", "sa", "");
        Connection conn = manager.openConnection();
        conn.prepareStatement("drop table if exists Person;").executeUpdate();
        conn.prepareStatement("create table if not exists Person(id integer primary key auto_increment, name varchar(10));").executeUpdate();
        conn.close();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdding() {
        try {
            PersonDao personDao = PersonDao.getInstance();
            String string1 = "Matti";
            String string2 = "Maija";
            personDao.create(new Person(0, string1));
            personDao.create(new Person(0, string2));
            Connection conn = manager.openConnection();
            PreparedStatement s = conn.prepareStatement("select * from Person;");
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                if (!rs.getString("name").equals(string1)) {
                    fail("The first name is wrong: expected '" + string1 + "', but got '" + rs.getString("name") + "'");
                }
            } else {
                fail("create() did not insert any lines to the table");
            }
            if (rs.next()) {
                if (!rs.getString("name").equals(string2)) {
                    fail("The second name is wrong: expected '" + string2 + "', but got '" + rs.getString("name") + "'");
                }
            } else {
                fail("The query returned only one result after two lines were added");
            }
            conn.close();
        } catch (SQLException e) {
            fail("SQL exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testReading() {
        try {
            Connection conn = manager.openConnection();
            PreparedStatement s = conn.prepareStatement("insert into Person (id, name) values (default, 'Matti');");
            s.executeUpdate();
            s = conn.prepareStatement("insert into Person (id, name) values (default, 'Maija');");
            s.executeUpdate();
            conn.close();
            PersonDao personDao = PersonDao.getInstance();
            Person matti = personDao.read("Matti");
            Person maija = personDao.read("Maija");
            if (matti.getId() != 1) {
                fail("Matti had the wrong database ID: expected 1, but got " + matti.getId());
            }
            if (maija.getId() != 2) {
                fail("Maija had the wrong database ID: expected 2, but got " + maija.getId());
            }
        } catch (SQLException e) {
            fail("SQL exception encountered: " + e.getMessage());
        }
    }

    @Test
    public void testListing() {
        try {
            Connection conn = manager.openConnection();
            PreparedStatement s = conn.prepareStatement("insert into Person (id, name) values (default, 'Matti');");
            s.executeUpdate();
            s = conn.prepareStatement("insert into Person (id, name) values (default, 'Maija');");
            s.executeUpdate();
            conn.close();
            PersonDao personDao = PersonDao.getInstance();
            List<Person> list = personDao.list();
            Person matti = list.get(0);
            Person maija = list.get(1);
            if (matti.getId() != 1) {
                fail("Matti had the wrong database ID: expected 1, but got " + matti.getId());
            }
            if (maija.getId() != 2) {
                fail("Maija had the wrong database ID: expected 2, but got " + maija.getId());
            }
        } catch (SQLException e) {
            fail("SQL exception encountered: " + e.getMessage());
        }
    }
}
