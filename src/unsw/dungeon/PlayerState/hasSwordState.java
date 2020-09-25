package unsw.dungeon.PlayerState;

import java.util.List;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Opponents;
import unsw.dungeon.Player;

public class hasSwordState implements PlayerState {

    private Player player;
    private Dungeon dungeon;
    /**
     * Constructor for initializing the has sword state for player
     * @param dungeon
     * @param player
     */
    public hasSwordState(Dungeon dungeon, Player player) {
        //super(x, y);
        this.dungeon = dungeon;
        this.player = player;
    }
    /**
     * Player with a sword kills enemy upon collision
     */
    @Override
    public void collideWithEnemy() {
        int x = player.getX();
        int y = player.getY();
        List<Entity> entities = dungeon.getEntity(x, y);
        //if(entities.size() == 1){ return; }
        for(Entity e : entities){
            if(e instanceof Opponents){
                ((Opponents) e).death(e);
                player.reduceSwordUsage();
            }
        }
    }
    
}