package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Sword;
import unsw.dungeon.PlayerState.NormalState;
import unsw.dungeon.PlayerState.hasSwordState;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
public class SwordTest {
    private Dungeon dungeon = new Dungeon(10, 10);

    @Test
    public void KillEnemyWithSword(){
        Player playerB = new Player(dungeon, 0, 0);
        Enemy enemyB = new Enemy(dungeon, 1, 0);
        Sword swordB = new Sword(0, 0);
        dungeon.addEntity(playerB);
        dungeon.setPlayer(playerB);
        dungeon.addEntity(enemyB);
        dungeon.addEntity(swordB);
        playerB.pickUp(swordB);
        dungeon.removeEntity(swordB);
        assertTrue("hasSwordState", playerB.getPlayerState() instanceof hasSwordState);
        playerB.moveRight();
        assertTrue("Enemy died from sword", dungeon.getEntity().size() == 1 && dungeon.getEnemyCount() == 0);
        dungeon.removeEntity(playerB);
        dungeon.removeEntity(enemyB);
    }

    @Test
    public void RunOutOfSwordUses(){
        Player playerC = new Player(dungeon, 0, 0);
        Enemy enemyC = new Enemy(dungeon, 1, 0);
        Sword swordC = new Sword(0, 0);
        dungeon.addEntity(playerC);
        dungeon.addEntity(enemyC);
        dungeon.addEntity(swordC);
        dungeon.setPlayer(playerC);
        playerC.pickUp(swordC);
        dungeon.removeEntity(swordC);
        playerC.reduceSwordUsage();
        playerC.reduceSwordUsage();
        playerC.reduceSwordUsage();
        playerC.reduceSwordUsage();
        playerC.reduceSwordUsage();
        assertTrue("Sword has no use left", playerC.getSwordUses() == 0);
        assertTrue("NormalState", playerC.getPlayerState() instanceof NormalState);
        playerC.moveRight();
        assertTrue("Player died from enemy", dungeon.getEntity().size() == 1 && dungeon.getEnemyCount() == 1);
        dungeon.removeEntity(playerC);
        dungeon.removeEntity(enemyC);
    }
}