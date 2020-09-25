package unsw.dungeon.enemyStrategy;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Opponents;
//import unsw.dungeon.Player;

public class enemyMovement{
    private enemyStrategy strategy;

    public enemyMovement(enemyStrategy strategy){
        this.strategy = strategy;
    }

    public void executeStrategy(Opponents e, Entity p){
        strategy.moveStrategy(e, p);
    }

    public void setDungeon(Dungeon dungeon){
        strategy.setDungeon(dungeon);
    }
}