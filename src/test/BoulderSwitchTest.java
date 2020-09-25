package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Switch;
import unsw.dungeon.Player;
public class BoulderSwitchTest {
    private Dungeon dungeon = new Dungeon(10, 10);
    @Test
    public void pushBoulder(){
        Player player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);
        // Player can't push more than one boulder nor move into the boulder
        Boulder boulderA = new Boulder(dungeon, 1, 0);
        Boulder boulderB = new Boulder(dungeon, 2, 0);
        Boulder boulderC = new Boulder(dungeon, 1, 1);
        dungeon.addEntity(boulderA);
        dungeon.addEntity(boulderB);
        dungeon.addEntity(boulderC);
        player.pushRight(boulderA);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
        assertEquals(1, boulderA.getX());
        assertEquals(0, boulderA.getY());
        assertEquals(2, boulderB.getX());
        assertEquals(0, boulderB.getY());

        player.moveDown();
        player.pushRight(boulderC);
        assertEquals(2, boulderC.getX());
        assertEquals(1, boulderC.getY());
    }

    @Test
    public void switchTest(){
        Player player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);
        
        Boulder boulderA = new Boulder(dungeon, 1, 0);
        dungeon.addEntity(boulderA);
        Switch sw1 = new Switch(2, 0);
        dungeon.addEntity(sw1);
        player.pushRight(boulderA);
        // on the top of switch
        assertEquals(sw1.getX(), boulderA.getX());
        assertEquals(sw1.getY(), boulderA.getY());
        assertTrue("triggered", sw1.getStatus());

        player.pushRight(boulderA);
        assertFalse("untriggered", sw1.getStatus());
    }
    
    @Test
    public void pushToClosedDoor(){
        Dungeon dungeonA = new Dungeon(10, 10);
        Player player = new Player(dungeonA, 0, 0);
        Boulder boulder = new Boulder(dungeonA, 1, 0);
        Door door = new Door(2, 0, 1);
        dungeonA.addEntity(player);
        dungeonA.setPlayer(player);
        dungeonA.addEntity(boulder);
        dungeonA.addEntity(door);

        player.pushRight(boulder);

        assertEquals(0, player.getX());
        assertEquals(0, player.getY());

        assertEquals(1, boulder.getX());
        assertEquals(0, boulder.getY());
    }
}