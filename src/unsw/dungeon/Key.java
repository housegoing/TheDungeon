package unsw.dungeon;

public class Key extends Pickable{

    private int id;
    /**
     * Constructor initialize key instance on position (x,y)
     * @param x
     * @param y
     * @param id
     */
    public Key(int x, int y, int id) {
        super(x, y);
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}