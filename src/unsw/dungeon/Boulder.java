package unsw.dungeon;

import java.util.List;

public class Boulder extends Entity{
    
    private Dungeon dungeon;
    private Switch dungeonSwitch;
    /**
     * Constructor for initializing the boulder instance on position (x,y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.dungeonSwitch = null;
        checkSwitch();
    }
    /**
     * Boulder can be pushed by player towards different directions
     * Need to check if a switch is activated every time boulder is pushed
     */
    public void pushUp() {
        if (getY() > 0)
            y().set(getY() - 1);
        checkSwitch();
    }

    public void pushDown() {
        if (getY() < dungeon.getHeight() - 1)
            y().set(getY() + 1);
        checkSwitch();
    }

    public void pushLeft() {
        if (getX() > 0)
            x().set(getX() - 1);
        checkSwitch();
    }

    public void pushRight() {
        if (getX() < dungeon.getWidth() - 1)
            x().set(getX() + 1);
        checkSwitch();
    }
    /**
     * Check if the movement is valid
     * Can't move through wall, closed door or another boulder
     * @param direction
     * @return
     */
    public boolean checkMovement(String direction) {
        int nextX, nextY;
        nextX = getX();
        nextY = getY();
        // X = column, Y = row
        if(direction.equals("UP")){
            nextY -= 1;
        }else if(direction.equals("DOWN")){
            nextY += 1;
        }else if(direction.equals("LEFT")){
            nextX -= 1;
        }else if(direction.equals("RIGHT")){
            nextX += 1;
        }
        // Check if out of bound
        if(nextX < 0 || nextY < 0 || nextX > dungeon.getWidth() - 1 || nextY > dungeon.getHeight() - 1){
            return false;
        }

        List<Entity> entities = dungeon.getEntity(nextX, nextY);
        if(entities.isEmpty()){
            return true;
        }

        for(Entity e:entities){
            if(e instanceof Wall || e instanceof Boulder || e instanceof Portal){
                return false;
            }else if(e instanceof Door && !((Door) e).isOpen()){
                return false;
            }else if(e instanceof Enemy){
                // Boulder being pushed to enemy will kill the enemy
                ((Enemy) e).death(e);
            }
        }

        return true;
    }
    /**
     * Assign a switch to the boudler if the boudler activates the switch
     * Deactivate the switch if boulders moves away and remove the assignment
     */
    public void checkSwitch(){
        List<Entity> entities = dungeon.getEntity(getX(), getY());
        for(Entity e : entities){
            if(e instanceof Switch){
                dungeonSwitch = (Switch) e;
                dungeonSwitch.setStatus(true);
                return;
            }
        }
        if(dungeonSwitch != null){
            dungeonSwitch.setStatus(false);
            dungeonSwitch = null;
        }
    }
}