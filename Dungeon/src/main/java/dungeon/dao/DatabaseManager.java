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

    private DatabaseManager() {
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
