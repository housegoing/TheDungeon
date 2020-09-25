package unsw.dungeon.enemyStrategy;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Opponents;
//import unsw.dungeon.Player;

public interface enemyStrategy {
    public void moveStrategy(Opponents enemy, Entity Entity);
    public void setDungeon(Dungeon dungeon);
}