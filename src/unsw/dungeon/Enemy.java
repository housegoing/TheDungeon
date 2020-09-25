package unsw.dungeon;

import java.util.List;

import unsw.dungeon.PlayerState.InvincibleState;
import unsw.dungeon.PlayerState.PlayerState;
import unsw.dungeon.enemyStrategy.chasePlayer;
import unsw.dungeon.enemyStrategy.enemyMovement;
import unsw.dungeon.enemyStrategy.runAway;

public class Enemy extends Opponents{

    private Dungeon dungeon;
    private String lastMovement;
    /**
     * Constructor initialize enemy instance on position (x,y)
     * @param dungeon
     * @param x
     * @param y
     */
    public Enemy(Dungeon dungeon, int x, int y) {
        super(dungeon, x, y);
        this.dungeon = dungeon;
        lastMovement = " ";
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
        //System.out.println("Enemy pos: " + getX() + " " + getY());
    }

    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1)
            y().set(getY() + 1);
        //System.out.println("Enemy pos: " + getX() + " " + getY());
    }

    public void moveLeft() {
        if (getX() > 0)
            x().set(getX() - 1);
        //System.out.println("Enemy pos: " + getX() + " " + getY());
    }

    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1)
            x().set(getX() + 1);
        //System.out.println("Enemy pos: " + getX() + " " + getY());
    }

    public void patrol(){
        PlayerState state = dungeon.getPlayerState();
        if(state instanceof InvincibleState){
            enemyMovement move = new enemyMovement(new runAway());
            move.executeStrategy(this, dungeon.getPlayer());
        }else{
            enemyMovement move = new enemyMovement(new chasePlayer());
            move.executeStrategy(this, dungeon.getPlayer());
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
     * Remove this enemy from dungeon and UI
     * @param e this enemy object
     */
    @Override
    public void death(Entity e){
        dungeon.removeEntity(e);
        e.remove();
    }
}