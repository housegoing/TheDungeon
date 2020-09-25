package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.InvincibilityPotion;
import unsw.dungeon.Player;
import unsw.dungeon.Sword;
import unsw.dungeon.PlayerState.InvincibleState;
import unsw.dungeon.PlayerState.NormalState;
import unsw.dungeon.PlayerState.hasSwordState;

public class InvinciblePotionTest {

    private Dungeon dungeon = new Dungeon(10, 10);

    @Test
    public void KillEnemyWithCollision(){
        Player playerD = new Player(dungeon, 0, 0);
        Enemy enemyD = new Enemy(dungeon, 1, 0);
        InvincibilityPotion potionD = new InvincibilityPotion(0, 0);
        dungeon.addEntity(playerD);
        dungeon.setPlayer(playerD);
        dungeon.addEntity(enemyD);
        dungeon.addEntity(potionD);
        playerD.pickUp(potionD);
        dungeon.removeEntity(potionD);
        assertTrue("InvincibleState", playerD.getPlayerState() instanceof InvincibleState);
        playerD.moveRight();
        assertTrue("Enemy died from potion", dungeon.getEntity().size() == 1 && dungeon.getEnemyCount() == 0);
        dungeon.removeEntity(playerD);
        dungeon.removeEntity(enemyD);
    }

    @Test
    public void PotionRunOut() throws InterruptedException {
        // Potion run out
        Player playerE = new Player(dungeon, 0, 0);
        Enemy enemyE = new Enemy(dungeon, 1, 0);
        InvincibilityPotion potionE = new InvincibilityPotion(0, 0);
        potionE.setDuration(2000);
        dungeon.addEntity(playerE);
        dungeon.setPlayer(playerE);
        dungeon.addEntity(enemyE);
        dungeon.addEntity(potionE);
        playerE.pickUp(potionE);
        dungeon.removeEntity(potionE);
        Thread.sleep(2000);
        assertTrue("NormalState here", playerE.getPlayerState() instanceof NormalState);
        playerE.moveRight();
        assertTrue("Player died from enemy", dungeon.getEntity().size() == 1 && dungeon.getEnemyCount() == 1);
        dungeon.removeEntity(playerE);
        dungeon.removeEntity(enemyE);

        // Potion run out but has sword
        Player playerF = new Player(dungeon, 0, 0);
        Enemy enemyF = new Enemy(dungeon, 1, 0);
        InvincibilityPotion potionF = new InvincibilityPotion(0, 0);
        Sword swordF = new Sword(0, 0);
        potionF.setDuration(2000);
        dungeon.addEntity(playerF);
        dungeon.setPlayer(playerF);
        dungeon.addEntity(enemyF);
        dungeon.addEntity(potionF);
        dungeon.addEntity(swordF);
        playerF.pickUp(potionF);
        playerF.pickUp(swordF);
        dungeon.removeEntity(potionF);
        dungeon.removeEntity(swordF);
        Thread.sleep(2000);
        assertTrue("hasSwordState", playerF.getPlayerState() instanceof hasSwordState);
        playerF.moveRight();
        assertTrue("Enemy died from sword", dungeon.getEntity().size() == 1 && dungeon.getEnemyCount() == 0);
        dungeon.removeEntity(playerF);
        dungeon.removeEntity(enemyF);
    }
}