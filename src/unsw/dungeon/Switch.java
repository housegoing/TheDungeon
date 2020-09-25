package unsw.dungeon;

public class Switch extends Entity{

    private boolean status;
    /**
     * Constructor initialize switch instance on position (x,y)
     * @param x
     * @param y
     */
    public Switch(int x, int y){
        super(x, y);
        status = false;
    }

    public boolean getStatus(){
        return status;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
}