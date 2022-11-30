package av4.alien;

public class MarshmallowManAlien extends Alien{
    public MarshmallowManAlien(int health, String name) {
        super(health, name);
    }

    @Override
    public AlienType type() {
        return AlienType.MARSHMALLOW_MAN_ALIEN;
    }
}
