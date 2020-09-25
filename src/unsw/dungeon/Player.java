package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import unsw.dungeon.PlayerState.ImmuneState;
import unsw.dungeon.PlayerState.InvincibleState;
import unsw.dungeon.PlayerState.NormalState;
import unsw.dungeon.PlayerState.PlayerState;
import unsw.dungeon.PlayerState.hasSwordState;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity{

    private Dungeon dungeon;
    private List<Key> key;
    private Sword sword;
    private PlayerState playerState;
    private BooleanProperty alive;
    private IntegerProperty health;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     * @param dungeon
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.key = new ArrayList<Key>();
        this.sword = null;
        this.playerState = new NormalState(dungeon, this);
        this.alive = new SimpleBooleanProperty(true);
        this.health = new SimpleIntegerProperty(3);
    }
    /**
     * Move the player to set direciton if the conditions are met
     */
    public void moveUp() {
        if(checkMovement("UP")){
            if (getY() > 0)
                y().set(getY() - 1);
        }
        collideWithEnemy();
    }

    public void moveDown() {
        if(checkMovement("DOWN")){
            if (getY() < dungeon.getHeight() - 1)
                y().set(getY() + 1);
        }
        collideWithEnemy();
    }

    public void moveLeft() {
        if(checkMovement("LEFT")){
            if (getX() > 0)
                x().set(getX() - 1);
        }
        collideWithEnemy();
    }

    public void moveRight() {
        if(checkMovement("RIGHT")){
            if (getX() < dungeon.getWidth() - 1)
                x().set(getX() + 1);
        }
        collideWithEnemy();
    }
    /**
     * Push the boulder towards set direction if the conditions are met
     * @param boulder
     */
    public void pushUp(Boulder boulder) {
        if(boulder != null && boulder.checkMovement("UP")){
            boulder.pushUp();
            if (getY() > 0)
                y().set(getY() - 1);
        }
    }

    public void pushDown(Boulder boulder) {
        if(boulder != null && boulder.checkMovement("DOWN")){
            boulder.pushDown();
            if (getY() < dungeon.getHeight() - 1)
                y().set(getY() + 1);
        }
    }

    public void pushLeft(Boulder boulder) {
        if(boulder != null && boulder.checkMovement("LEFT")){
            boulder.pushLeft();
            if (getX() > 0)
                x().set(getX() - 1);
        }
        
    }

    public void pushRight(Boulder boulder) {
        if(boulder != null && boulder.checkMovement("RIGHT")){
            boulder.pushRight();
            if (getX() < dungeon.getWidth() - 1)
                x().set(getX() + 1);
        }
    }
    /**
     * Player behave differently to different entities 
     * @param e the entity that player want to pickup
     * @return
     */
    public boolean pickUp(Entity e){
        if(e instanceof Key){
            // Check if player already has a key, if not pick up the key
            if(key.size() == 0){
                key.add((Key) e);
                return true;
            }
        }else if(e instanceof Treasure){
            return true;
        }else if(e instanceof Sword){
            // Check if player already has a sword, if not pick up the sword
            if(this.sword == null || this.sword.getUses() == 0){
                this.sword = new Sword(getX(), getY());
                setPlayerState(new hasSwordState(dungeon, this));
                return true;
            }
        }else if(e instanceof InvincibilityPotion){
            // Stack the potion time of player already has a potion
            // Otherwise put player in invincible mode
            if(this.playerState instanceof InvincibleState){
                ((InvincibleState) playerState).addDuration(10*1000);
            }else{
                this.playerState = new InvincibleState(dungeon, this, (InvincibilityPotion) e);
            }
            return true;
        }else if(e instanceof Apple){
            // Heal the player
            increaseHealth();
            return true;
        }
        return false;
    }
    /**
     * Check if the holding key can open this door
     * @param door
     * @return
     */
    public boolean openDoor(Door door){
        if(key.size() == 0){ return false; }
        else{
            if(door.checkKey(key.get(0).getId())){
                key.remove(0);
                return true;
            }
        }
        return false;
    }
    /**
     * Set player location to location of given portal
     * @param portal
     */
    public void teleport(Portal portal){
        setX(portal.getX());
        setY(portal.getY());
    }
    /**
     * Check if player movement towards the direction is valid
     * @param direction
     * @return
     */
    public boolean checkMovement(String direction){
        collideWithEnemy();
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
        // If obstacles present on the way, player can move in that direction
        List<Entity> entities = dungeon.getEntity(nextX, nextY);
        if(entities.isEmpty()){
            return true;
        }
        // Player can't move through walls, doors or boulder without pressing SHIFT
        for(Entity e:entities){
            if(e instanceof Wall || e instanceof Boulder){
                return false;
            }else if(e instanceof Door && !((Door) e).isOpen()){
                return false;
            }
        }

        return true;
    }
    
    public void setPlayerState(PlayerState playerState){
        this.playerState = playerState;
    }

    public void changeToNormal(){
        setPlayerState(new NormalState(dungeon, this));
    }

    public void changeToHasSword(){
        setPlayerState(new hasSwordState(dungeon, this));
    }

    public void changeToImmune(){
        setPlayerState(new ImmuneState(dungeon, this));
    }

    public PlayerState getPlayerState(){
        return this.playerState;
    }

    public void collideWithEnemy(){
        playerState.collideWithEnemy();
    }
    /**
     * Remove the player from the dungeon and UI
     * @param e the player object
     */
    public void death(Entity e){
        alive.setValue(false);
        dungeon.removeEntity(e);
        e.remove();
    }

    public void reduceSwordUsage(){
        this.sword.reduceUses();
        if(this.sword.getUses() == 0){
            changeToNormal();
        }
    }

    public int getSwordUses(){
        if(this.sword == null){
            return 0;
        }
        return this.sword.getUses();
    }

    public BooleanProperty isAlive(){
        return alive;
    }
    /**
     * Reduces health when damage taken
     * The health bar in UI change respectively
     */
    public void reduceHealth(){
        health.setValue(health.getValue() - 1);
        List<Entity> entities = dungeon.getEntity();
        // Get health bar and reduce it
        for(Entity e : entities){
            if(e instanceof Heart){
                if(health.getValue() == 2 && e.getX() == 2 && e.getY() == 0){
                    e.remove();
                    break;
                }else if(health.getValue() == 1 && e.getX() == 1 && e.getY() == 0){
                    e.remove();
                    break;
                }else if(health.getValue() == 0 && e.getX() == 0 && e.getY() == 0){
                    e.remove();
                    break;
                }
            }
        }
        // Player dies if health is 0
        if(health.getValue() == 0){
            death(this);
        }
    }
    /**
     * Increase health if apple is picked up
     */
    public void increaseHealth(){
        // Player can't exceed maximum 3 life
        if(health.getValue() >= 3){ System.out.println(health.getValue()); return; }
        health.setValue(health.getValue() + 1);
        List<Entity> entities = dungeon.getEntity();
        // Increase health on UI
        for(Entity e : entities){
            if(e instanceof Heart){
                if(health.getValue() == 3 && e.getX() == 2 && e.getY() == 0){
                    e.show();
                    break;
                }else if(health.getValue() == 2 && e.getX() == 1 && e.getY() == 0){
                    e.show();
                    break;
                }else if(health.getValue() == 1 && e.getX() == 0 && e.getY() == 0){
                    e.show();
                    break;
                }
            }
        }
    }

    public IntegerProperty getHealth(){
        return health;
    }
}
