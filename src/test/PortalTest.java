package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Player;
import unsw.dungeon.Portal;
public class PortalTest {
    private Dungeon dungeon = new Dungeon(10, 10);
    @Test
    public void TeleportTest(){
        Player player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);

        Portal p1_1 = new Portal(1, 0, 1);
        Portal p1_2 = new Portal(8, 8, 1);

        Portal p2_1 = new Portal(3, 0, 2);
        Portal p2_2 = new Portal(7, 8, 2);

        dungeon.addEntity(p1_1);
        dungeon.addEntity(p1_2);
        dungeon.addEntity(p2_1);
        dungeon.addEntity(p2_2);

        player.moveRight();
        player.teleport(dungeon.getPairPortal(p1_1));

        assertTrue("player x is 8", player.getX() == 8);
        assertTrue("player y is 8", player.getY() == 8);

        player.moveLeft();

        player.teleport(dungeon.getPairPortal(p2_2));

        assertTrue("player x is 3", player.getX() == 3);
        assertTrue("player y is 0", player.getY() == 0);
    }
}