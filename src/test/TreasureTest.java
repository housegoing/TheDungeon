package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Treasure;
import unsw.dungeon.Player;
public class TreasureTest {
    private Dungeon dungeon = new Dungeon(10, 10);
    @Test
    public void TreasurePickable(){
        Player player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);
        Treasure t1 = new Treasure(1, 0);
        Treasure t2 = new Treasure(1, 0);

        dungeon.addEntity(t1);
        dungeon.addEntity(t2);
        
        player.moveRight();
        assertTrue("can pick up t1",player.pickUp(t1));
        player.moveRight();
        assertTrue("can pick up t2",player.pickUp(t2));
    }
}