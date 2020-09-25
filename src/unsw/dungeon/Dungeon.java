/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import unsw.dungeon.Goals.Goal;
import unsw.dungeon.PlayerState.PlayerState;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private Goal goal;
    /**
     * Constructor for initialize the dungeon instance
     * @param width
     * @param height
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.goal = null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerX(){
        return player.getX();
    }

    public int getPlayerY(){
        return player.getY();
    }

    public PlayerState getPlayerState(){
        return player.getPlayerState();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGoal(Goal goal){
        this.goal = goal;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity){
        entities.remove(entity);
    }
    /**
     * Give all the entities currently in the dungeon
     * @return
     */
    public List<Entity> getEntity(){
        return this.entities;
    }
    /**
     * Check if goal is complete
     */
    public void checkGoal(){
        if(goal != null){
            if(goal.isComplete()){
                System.out.println("You Win");
            }
        }
    }

    public BooleanProperty getGoalProperty(){
        return goal.getProperty();
    }
    /**
     * Get current enemies
     * @return
     */
    public List<Opponents> getEnemies(){
        List<Opponents> eList = new ArrayList<Opponents>();
        for(Entity e : entities){
            if(e != null){
                if(e instanceof Opponents){
                    eList.add((Opponents)e);
                }
            }
        }
        return eList;
    }
    /**
     * Get entities on position (x,y)
     * @param x
     * @param y
     * @return
     */
    public List<Entity> getEntity(int x, int y){
        List<Entity> eList = new ArrayList<Entity>();
        if(x < 0 || y < 0 || x > getWidth() - 1 || y > getHeight() - 1){
            return eList;
        }
        for(Entity e : entities){
            if(e != null){
                if(e.getX() == x && e.getY() == y){
                    eList.add(e);
                }
            }
        }
        return eList;
    }
    /**
     * Return the amount of enemy on the map
     * @return
     */
    public int getEnemyCount(){
        int count = 0;
        for(Entity e : entities){
            if(e instanceof Opponents){
                count++;
            }
        }
        return count;
    }
    /**
     * Return the amount of treasure on the map
     * @return
     */
    public int getTreasureCount(){
        int count = 0;
        for(Entity e : entities){
            if(e instanceof Treasure){
                count++;
            }
        }
        return count;
    }
    /**
     * Return the first treasure found in the dungeon
     * @return
     */
    public Treasure getTreasure(){
        for(Entity e : entities){
            if(e instanceof Treasure){
                return (Treasure)e;
            }
        }
        return null;
    }
    /**
     * Convert the map into a matrix for BFS
     * @return
     */
    public int[][] getMatrix(){
        int[][] matrix = new int[height][width];
        for(int x = 0; x < height; x++){
            for(int y = 0; y < width; y++){
                List<Entity> entities = getEntity(y, x);
                if(entities.size() == 0){
                    matrix[x][y] = 1;
                    continue;
                }
                for(Entity e: entities){
                    if(e instanceof Wall || e instanceof Boulder || (e instanceof Door && ((Door)e).isOpen() == false)){
                        matrix[x][y] = 0;
                        break;
                    }else{
                        matrix[x][y] = 1;
                    }
                }
            }
        }
        return matrix;
    }
    /**
     * Show the amount of switch that has not been activated
     * @return
     */
    public int getUnactivedSwitchCount(){
        int count = 0;
        for(Entity e : entities){
            if(e instanceof Switch){
                if(!((Switch)e).getStatus()){
                    count++;
                }
            }
        }
        return count;
    }
    /**
     * Check the top, right, bottom and left block to see if there is a door
     * If so return the door
     * @param x
     * @param y
     * @return
     */
    public List<Door> getDoor(int x, int y){
        List<Door> doors = new ArrayList<Door>();
        List<Entity> entities = new ArrayList<Entity>();
        if(y > 0 && y < getHeight() - 1){
            entities.addAll(getEntity(x, y-1));
            entities.addAll(getEntity(x, y+1));
        }
        if(x > 0 && x < getWidth() - 1){
            entities.addAll(getEntity(x-1, y));
            entities.addAll(getEntity(x+1, y));
        }
        
        for(Entity e : entities){
            if(e instanceof Door){
                doors.add((Door) e);
            }
        }
        return doors;
    }
    /**
     * Go through the map to check for the paired portal 
     * @param portal
     * @return
     */
    public Portal getPairPortal(Portal portal){
        for(Entity e : entities){
            if(e instanceof Portal){
                if(!((Portal) e).equals(portal)){
                    if(((Portal) e).getId() == portal.getId()){
                        return (Portal) e;
                    }
                }
            }
        }
        return null;
    }
}
