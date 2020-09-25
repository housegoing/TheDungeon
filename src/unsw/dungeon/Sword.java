package unsw.dungeon;

public class Sword extends Pickable{

    private int uses;
    /**
     * Constructor initialize sword instance on position (x,y)
     * @param x
     * @param y
     */
    public Sword(int x, int y) {
        super(x, y);
        this.uses = 5;
    }

    public void reduceUses(){
        this.uses--;
    }

    public int getUses(){
        return this.uses;
    }
}