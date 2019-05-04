/*
 * @author londes
 */
package dungeon.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A singleton class for handling database settings. Trying to avoid pulling
 * Spring dependencies due to complexity.
 *
 * @author londes
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    private String path;
    private String username;
    private String password;
    private int charLimit;

    private DatabaseManager() {
        charLimit = 10;
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Sets up the database variables. Run this before openConnection.
     *
     * @param path
     * @param username
     * @param password
     */
    public void setup(String path, String username, String password) {
        this.path = path;
        this.username = username;
        this.password = password;
    }

    public void createTablesIfAbsent() throws SQLException {
        Connection conn = openConnection();
        conn.prepareStatement("create table if not exists Person(id integer primary key auto_increment,"
                + " name varchar(" + charLimit + "));").executeUpdate();
        conn.prepareStatement("create table if not exists Record(id integer primary key auto_increment,"
                + " personId integer, score integer, difficulty integer,"
                + " foreign key (personId) references Person(id));").executeUpdate();
        conn.close();
    }

    public int getCharLimit() {
        return charLimit;
    }

    /**
     * Get a connection from DriverManager. Run setup before this.
     *
     * @return
     * @throws SQLException
     */
    public Connection openConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(path, username, password);
        return conn;
    }
}
