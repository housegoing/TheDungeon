package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import unsw.dungeon.PlayerState.InvincibleState;
import unsw.dungeon.PlayerState.PlayerState;
import unsw.dungeon.enemyStrategy.enemyMovement;
import unsw.dungeon.enemyStrategy.fetchTreasure;
import unsw.dungeon.enemyStrategy.runAway;

public class Gnome extends Opponents{

    private Dungeon dungeon;
    private List<Treasure> treasure;
    private String lastMovement;
    /**
     * Constructor for initializing the gnome
     * @param dungeon
     * @param x
     * @param y
     */
    public Gnome(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.dungeon = dungeon;
        this.treasure = new ArrayList<Treasure>();
        this.lastMovement = " ";
    }

    public void setLastMovement(String move){
        this.lastMovement = move;
    }

    public String getLastMovement(){
        return this.lastMovement;
    }

    public void moveUp() {
        if (getY() > 0)
            y().set(getY() - 1);
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1)
            y().set(getY() + 1);
    }

    public void moveLeft() {
        if (getX() > 0)
            x().set(getX() - 1);
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1)
            x().set(getX() + 1);
    }
    /**
     * Gnome is always trying to get to the treasure
     * If there are no treasure on the map, the gnome will always try to run away from player
     */
    public void patrol(){
        PlayerState state = dungeon.getPlayerState();
        if(state instanceof InvincibleState || dungeon.getTreasureCount() == 0){
            enemyMovement move = new enemyMovement(new runAway());
            move.executeStrategy(this, dungeon.getPlayer());
        }else{
            enemyMovement move = new enemyMovement(new fetchTreasure());
            move.setDungeon(dungeon);
            move.executeStrategy(this, dungeon.getTreasure());
            pickup();
        }
    }

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
        // If nothing in front then move on
        if(entities.isEmpty()){
            return true;
        }
        // Can't through walls, boulders, and closed doors
        for(Entity e:entities){
            if(e instanceof Wall || e instanceof Boulder){
                return false;
            }else if(e instanceof Door && !((Door) e).isOpen()){
                return false;
            }
        }
        return true;
    }
    /**
     * When on top of the treasure, pick it up
     */
    public void pickup(){
        List<Entity> entities = dungeon.getEntity(getX(), getY());
        for(Entity e : entities){
            if(e instanceof Treasure){
                treasure.add((Treasure) e);
                dungeon.removeEntity(e);
                e.remove();
            }
        }
    }

    /**
     * Remove this gnome from dungeon
     * @param e this gnome object
     */
    @Override
    public void death(Entity e){
        dropTreasure();
        treasure.clear();
        dungeon.removeEntity(e);
        e.remove();
    }
    /**
     * Upon death drop the treasure
     */
    public void dropTreasure(){
        if(treasure.size() != 0){
            Treasure t = treasure.get(0);
            t.setX(getX());
            t.setY(getY());
            dungeon.addEntity(t);
            t.show();
        }
    }
}