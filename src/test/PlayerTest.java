package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Boulder;
import unsw.dungeon.Door;
import unsw.dungeon.Dungeon;
import unsw.dungeon.InvincibilityPotion;
import unsw.dungeon.Key;
import unsw.dungeon.Sword;
import unsw.dungeon.Wall;
import unsw.dungeon.Treasure;
import unsw.dungeon.Player;

public class PlayerTest {

    private Dungeon dungeon = new Dungeon(10, 10);
    private Player player = new Player(dungeon, 0, 0);

    @Test
    public void BasicMovementTest(){
        dungeon.setPlayer(player);

        // Player can move right
        player.moveRight();
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());

        // Player can move left
        player.moveLeft();
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());

        // Player can move down
        player.moveDown();
        assertEquals(0, player.getX());
        assertEquals(1, player.getY());

        // Player can move up
        player.moveUp();
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    public void obstacleTest(){
        dungeon.setPlayer(player);

        // Player shouldn't be able to move into walls
        Wall wall = new Wall(1, 0);
        dungeon.addEntity(wall);
        player.moveRight();
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());

        // Player can't push more than one boulder nor move into the boulder
        // Hence will be blocked
        Boulder boulderA = new Boulder(dungeon, 1, 0);
        Boulder boulderB = new Boulder(dungeon, 2, 0);
        dungeon.addEntity(boulderA);
        dungeon.addEntity(boulderB);
        player.pushRight(boulderA);
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());
        assertEquals(1, boulderA.getX());
        assertEquals(0, boulderA.getY());
        assertEquals(2, boulderB.getX());
        assertEquals(0, boulderB.getY());

        // Player will be blocked by closed doors
        Door door = new Door(0, 1, 1);
        dungeon.addEntity(door);
        player.moveDown();
        assertFalse("Door Closed", door.getStatus().getValue());
        assertEquals(0, player.getX());
        assertEquals(0, player.getY());

        // Player can't push boulder through closed doors
        
    }

    @Test
    public void MoveThroughTest(){
        dungeon.setPlayer(player);

        // Player should be able to push a single boulder
        Boulder boulder = new Boulder(dungeon, 1, 0);
        dungeon.addEntity(boulder);
        player.pushRight(boulder);
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
        assertEquals(2, boulder.getX());
        assertEquals(0, boulder.getY());

        // Player should be able to walk through opened doors
        Door doorA = new Door(1, 1, 1);
        doorA.open();
        dungeon.addEntity(doorA);
        player.moveDown();
        assertTrue("Door Opened", doorA.getStatus().getValue());
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());


        // Player should be able to push boulder through opened doors
        Door doorB = new Door(2, 0, 2);
        doorB.open();
        dungeon.addEntity(doorB);
        player.moveUp();
        player.pushRight(boulder);
        assertTrue("Door Opened", doorB.getStatus().getValue());
        assertEquals(2, player.getX());
        assertEquals(0, player.getY());
        assertEquals(3, boulder.getX());
        assertEquals(0, boulder.getY());
    }

    @Test
    public void pickUpTest(){
        dungeon.setPlayer(player);

        // Player can pickup sword
        Sword swordA = new Sword(0, 0);
        dungeon.addEntity(swordA);
        assertTrue("Picked up", player.pickUp(swordA));
        assertEquals(5, player.getSwordUses());
        
        // Player can't have two swords at once (more than 5 uses)
        Sword swordB = new Sword(0, 0);
        dungeon.addEntity(swordB);
        assertFalse("Can't pick up", player.pickUp(swordB));
        assertEquals(5, player.getSwordUses());
        dungeon.removeEntity(swordB);

        // Player can pickup treasure
        Treasure treasureA = new Treasure(0, 0);
        dungeon.addEntity(treasureA);
        assertTrue("Picked up", player.pickUp(treasureA));

        // Player can pickup invincible potion
        InvincibilityPotion potionA = new InvincibilityPotion(0, 0);
        dungeon.addEntity(potionA);
        assertTrue("Picked up", player.pickUp(potionA));

        // Player can stack potions
        InvincibilityPotion potionB = new InvincibilityPotion(0, 0);
        dungeon.addEntity(potionB);
        assertTrue("Picked up", player.pickUp(potionB));

        // Player can pickup key
        Key keyA = new Key(0, 0, 1);
        dungeon.addEntity(keyA);
        assertTrue("Picked up", player.pickUp(keyA));

        // Player can hold only one key at one time
        Key keyB = new Key(0, 0, 2);
        dungeon.addEntity(keyB);
        assertFalse("Picked up", player.pickUp(keyB));
    }
}