/*
 * @author londes
 */
package dungeon.dao;

import dungeon.dao.domain.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * A DAO for the class Person. Implemented using a singleton design pattern with
 * lazy loading.
 *
 * @author londes
 */
public class PersonDao implements Dao<Person> {

    private static PersonDao instance;

    public static PersonDao getInstance() {
        if (instance == null) {
            instance = new PersonDao();
        }
        return instance;
    }

    private PersonDao() {
    }

    @Override
    public Person create(Person person) throws SQLException {
        Person found = read(person.getName());
        if (found == null) {
            createNewPerson(person);
        } else {
            person = found;
        }
        return person;
    }

    private void createNewPerson(Person person) throws SQLException {
        Connection conn = openConnection();
        PreparedStatement s = conn.prepareStatement("insert into Person (id, name) values (default, ?);",
                Statement.RETURN_GENERATED_KEYS);
        s.setString(1, person.getName());
        s.executeUpdate();
        ResultSet keys = s.getGeneratedKeys();
        setUserId(keys, person);
        conn.close();
    }

    private void setUserId(ResultSet keys, Person person) throws SQLException {
        int id = -1;
        if (keys.next()) {
            id = keys.getInt(1);
        }
        person.setId(id);
    }

    @Override
    public Person read(Integer key) throws SQLException {
        Connection conn = openConnection();
        PreparedStatement s = conn.prepareStatement("select * from Person where id = ?;");
        Person person = null;
        s.setInt(1, key);
        ResultSet results = s.executeQuery();
        if (results.next()) {
            person = new Person(results.getInt("id"), results.getString("name"));
        }
        conn.close();
        return person;
    }

    public Person read(String name) throws SQLException {
        Connection conn = openConnection();
        PreparedStatement s = conn.prepareStatement("select * from Person where name = ?;");
        Person person = null;
        s.setString(1, name);
        ResultSet results = s.executeQuery();
        if (results.next()) {
            person = new Person(results.getInt("id"), results.getString("name"));
        }
        conn.close();
        return person;
    }

    @Override
    public List<Person> list() throws SQLException {
        Connection conn = openConnection();
        PreparedStatement s = conn.prepareStatement("select * from Person;");
        ResultSet results = s.executeQuery();
        List<Person> people = new ArrayList<>();
        while (results.next()) {
            Person person = new Person(results.getInt("id"), results.getString("name"));
            people.add(person);
        }
        conn.close();
        return people;
    }

    private Connection openConnection() throws SQLException {
        return DatabaseManager.getInstance().openConnection();
    }
}
