/*
 * @author londes
 */
package dungeon.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {

    public T create(T t) throws SQLException;

    public T read(Integer key) throws SQLException;

    public List<T> list() throws SQLException;

    public void delete(Integer key) throws SQLException;
}
