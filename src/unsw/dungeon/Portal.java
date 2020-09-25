package unsw.dungeon;

public class Portal extends Entity{

    private int id;
    /**
     * Constructor initialize portal instance on position (x,y)
     * @param x
     * @param y
     * @param id
     */
    public Portal(int x, int y, int id){
        super(x,y);
        this.id = id;
    }

    public int getId(){
        return id;
    }
}