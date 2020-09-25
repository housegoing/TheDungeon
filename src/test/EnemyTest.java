package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.PlayerState.NormalState;
import unsw.dungeon.Enemy;
import unsw.dungeon.Player;
public class EnemyTest {
    private Dungeon dungeon = new Dungeon(10, 10);

    @Test
    public void checkEnemyMove(){
        Player player = new Player(dungeon, 1, 0);
        Enemy enemy = new Enemy(dungeon, 3, 0);
        dungeon.addEntity(enemy);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        player.moveLeft();
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
        enemy.patrol();
        assertEquals(2, enemy.getX());
        assertEquals(0, enemy.getY());

        // Enemy try to get closer so move left
        player.moveDown();
        assertEquals(0, player.getX());
        assertEquals(1, player.getY());
        enemy.patrol();
        assertEquals(1, enemy.getX());
        assertEquals(0, enemy.getY());
        dungeon.removeEntity(player);
        dungeon.removeEntity(enemy);
    }

    @Test
    public void KillNormalPlayer(){
        Player playerA = new Player(dungeon, 0, 0);
        Enemy enemyA = new Enemy(dungeon, 1, 0);
        dungeon.addEntity(playerA);
        dungeon.setPlayer(playerA);
        dungeon.addEntity(enemyA);
        assertTrue("NormalState", playerA.getPlayerState() instanceof NormalState);
        playerA.moveRight();
        assertTrue("Player died from enemy", dungeon.getEntity().size() == 1 && dungeon.getEnemies().size() == 1);
        dungeon.removeEntity(playerA);
        dungeon.removeEntity(enemyA);
    }
}