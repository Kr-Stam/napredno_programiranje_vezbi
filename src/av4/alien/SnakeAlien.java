package av4.alien;

public class SnakeAlien extends Alien{
    public SnakeAlien(int type, int health, String name) {
        super(health, name);
    }

    @Override
    public AlienType type() {
        return AlienType.SNAKE_ALIEN;
    }
}
