package av4.alien;

public class OgreAlien extends Alien{
    public OgreAlien(int health, String name) {
        super(health, name);
    }

    @Override
    public AlienType type() {
        return AlienType.OGRE_ALIEN;
    }
}
