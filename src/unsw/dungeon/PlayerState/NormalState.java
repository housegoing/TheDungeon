package unsw.dungeon.PlayerState;

import java.util.List;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Entity;
import unsw.dungeon.Opponents;
import unsw.dungeon.Player;

public class NormalState implements PlayerState {

    private Player player;
    private Dungeon dungeon;

    /**
     * Constructor for initializing the normal state for player
     * @param dungeon
     * @param player
     */
    public NormalState(Dungeon dungeon, Player player){
        //super(x, y);
        this.dungeon = dungeon;
        this.player = player;
    }
    /**
     * Player with normal state receive damage when collide with enemy
     */
    @Override
    public void collideWithEnemy() {
        int x = player.getX();
        int y = player.getY();
        List<Entity> entities = dungeon.getEntity(x, y);
        if(entities.size() == 1){ return; }
        for(Entity e : entities){
            if(e instanceof Opponents){
                player.reduceHealth();
                player.changeToImmune();
            }
        }
    }
    
}