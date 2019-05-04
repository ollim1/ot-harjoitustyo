/*
 * @author londes
 */
package dungeon.dao.domain;

public class Person {

    private Integer id;
    private String name;

    public Person(Integer id, String name) {
        this.id = id;
        if (name == null) {
            name = "";
        }
        name = name.trim();

        if (name.length() > 10) {
            name = name.substring(0, 10);
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
