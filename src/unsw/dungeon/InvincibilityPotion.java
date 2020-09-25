package unsw.dungeon;

public class InvincibilityPotion extends Pickable{

    private int duration;
    /**
     * Constructor initialize invincibility potion instance on position (x,y)
     * @param x
     * @param y
     */
    public InvincibilityPotion(int x, int y) {
        super(x, y);
        // initial duration
        this.duration = 10 * 1000;
    }

    public int getDuration(){
        return this.duration;
    }

    public void setDuration(int duration){
        this.duration = duration;
    }
}