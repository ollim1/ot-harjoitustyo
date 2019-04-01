/*
 * @author olli m
 */
package dungeon.domain;

public class Monster extends Character {

    private static final int MAX_HEALTH = 10;

    private CharacterState state;
    // private Attack attack;
    // should probably make this an abstract class

    public Monster() {
        this.state = CharacterState.STAY;
    }

    public void setState(CharacterState state) {
        this.state = state;
    }

    @Override
    public void act(char[][] map) {
    }
}
