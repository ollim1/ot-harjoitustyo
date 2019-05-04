/*
 * @author londes
 */
package dungeon.dao.domain;

import dungeon.dao.DatabaseManager;

public class Person {

    private Integer id;
    private String name;

    public Person(Integer id, String name) {
        this.id = id;
        if (name == null) {
            name = "";
        }
        name = name.trim();

        int limit = DatabaseManager.getInstance().getCharLimit();
        if (name.length() > limit) {
            name = name.substring(0, limit);
        }
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
