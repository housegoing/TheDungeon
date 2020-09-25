package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Key;
import unsw.dungeon.Player;
public class DoorKeyTest {
    private Dungeon dungeon = new Dungeon(10, 10);

    @Test
    public void pickUpTwoKeyTest(){
        Player player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);
        Key key1 = new Key(1, 0, 1);
        Key key2 = new Key(2, 0, 2);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        player.moveRight();
        assertTrue("pickup key1 successfully", player.pickUp(key1));
        player.moveRight();
        assertFalse("pickup key2 failed", player.pickUp(key2));
    }

    @Test
    public void openDoorWithKeyTest(){
        Player player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);
        Key key1 = new Key(1, 0, 1);
        Key key2 = new Key(2, 0, 2);
        Door door1 = new Door(1, 2, 1);
        Door door2 = new Door(2, 2, 2);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        dungeon.addEntity(door1);
        dungeon.addEntity(door2);

        player.moveRight();
        player.pickUp(key1);

        player.moveRight();
        player.moveDown();
        
        assertFalse("opendoor2 failed", player.openDoor(door2));

        player.moveLeft();

        assertTrue("opendoor1 successfully", player.openDoor(door1));        

    }

    @Test
    public void checkDoorBlockedTest(){
        Player player = new Player(dungeon, 0, 0);
        dungeon.setPlayer(player);
        Key key1 = new Key(1, 0, 1);
        Key key2 = new Key(2, 0, 2);
        Door door1 = new Door(1, 2, 1);
        Door door2 = new Door(2, 2, 2);
        dungeon.addEntity(key1);
        dungeon.addEntity(key2);
        dungeon.addEntity(door1);
        dungeon.addEntity(door2);

        player.moveRight();
        player.pickUp(key1);

        player.moveRight();
        player.moveDown();
        
        assertFalse("opendoor2 failed", player.openDoor(door2));
        assertFalse("can't move down(blocked by door2)", player.checkMovement("DOWN"));

        player.moveLeft();
        assertTrue("opendoor1 successfully", player.openDoor(door1));
        assertTrue("can move through opened door1", player.checkMovement("DOWN"));
    }
}