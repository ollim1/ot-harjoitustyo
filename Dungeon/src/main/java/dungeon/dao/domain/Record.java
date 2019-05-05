/*
 * @author londes
 */
package dungeon.dao.domain;

import dungeon.domain.Difficulty;

/**
 * Represents game scores.
 *
 * @author londes
 */
public class Record {

    private Integer id;
    private Person person;
    private Integer score;
    private Difficulty difficulty;

    public Record(Integer id, Person person, Integer score, Difficulty difficulty) {
        this.id = id;
        this.person = person;
        this.score = score;
        this.difficulty = difficulty;
    }

    public Integer getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public Person getPerson() {
        return person;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
