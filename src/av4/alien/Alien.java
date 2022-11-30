package av4.alien;

public abstract class Alien {

    public abstract AlienType type();
    public int health; // 0=dead, 100=full strength
    public String name;

    public Alien(int health, String name) {
        this.health = health;
        this.name = name;
    }
}
