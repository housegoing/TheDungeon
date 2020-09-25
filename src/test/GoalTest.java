package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Exit;
import unsw.dungeon.Player;
import unsw.dungeon.Switch;
import unsw.dungeon.Treasure;
import unsw.dungeon.Goals.CompositeGoal;
import unsw.dungeon.Goals.EnemyGoal;
import unsw.dungeon.Goals.ExitGoal;
import unsw.dungeon.Goals.Goal;
import unsw.dungeon.Goals.SwitchGoal;
import unsw.dungeon.Goals.TreasureGoal;

public class GoalTest {

    @Test
    public void checkEnemyGoal(){
        Dungeon dungeon = new Dungeon(10, 10);
        Goal goal = new EnemyGoal(dungeon);
        dungeon.setGoal(goal);
        Enemy enemy = new Enemy(dungeon, 1, 0);
        dungeon.addEntity(enemy);
        assertFalse("Incomplete", goal.isComplete());
        enemy.death(enemy);
        assertTrue("Complete", goal.isComplete());
    }

    @Test
    public void checkSwitchGoal(){
        Dungeon dungeon = new Dungeon(10, 10);
        Goal goal = new SwitchGoal(dungeon);
        dungeon.setGoal(goal);
        Switch dungeonSwitch = new Switch(0, 0);
        dungeon.addEntity(dungeonSwitch);
        assertFalse("Incomplete", goal.isComplete());
        dungeonSwitch.setStatus(true);
        assertTrue("Complete", goal.isComplete());
    }

    @Test
    public void checkTreasureGoal(){
        Dungeon dungeon = new Dungeon(10, 10);
        Goal goal = new TreasureGoal(dungeon);
        dungeon.setGoal(goal);
        Treasure treasure = new Treasure(0, 0);
        dungeon.addEntity(treasure);
        assertFalse("Incomplete", goal.isComplete());
        dungeon.removeEntity(treasure);
        assertTrue("Complete", goal.isComplete());
    }

    @Test
    public void checkExitGoal(){
        Dungeon dungeon = new Dungeon(10, 10);
        Goal goal = new ExitGoal(dungeon);
        dungeon.setGoal(goal);
        Exit exit = new Exit(0, 0);
        Player player = new Player(dungeon, 1, 0);
        dungeon.addEntity(player);
        dungeon.setPlayer(player);
        dungeon.addEntity(exit);
        assertFalse("Incomplete", goal.isComplete());
        player.moveLeft();
        assertTrue("Complete", goal.isComplete());
    }

    @Test
    public void checkCompositeGoal(){
        Dungeon dungeon = new Dungeon(10, 10);
        CompositeGoal goal = new CompositeGoal("AND");
        Goal goal1 = new EnemyGoal(dungeon);
        Goal goal2 = new TreasureGoal(dungeon);
        goal.addGoal(goal1);
        goal.addGoal(goal2);
        dungeon.setGoal(goal);
        Enemy enemy = new Enemy(dungeon, 0, 0);
        Treasure treasure = new Treasure(0, 0);
        dungeon.addEntity(treasure);
        dungeon.addEntity(enemy);
        assertFalse("Incomplete", goal.isComplete());
        dungeon.removeEntity(treasure);
        dungeon.removeEntity(enemy);
        assertTrue("Complete", goal.isComplete());
    }

    @Test
    public void checkCompositeGoal2(){
        Dungeon dungeon = new Dungeon(10, 10);
        CompositeGoal goal = new CompositeGoal("OR");
        Goal goal1 = new EnemyGoal(dungeon);
        Goal goal2 = new TreasureGoal(dungeon);
        goal.addGoal(goal1);
        goal.addGoal(goal2);
        dungeon.setGoal(goal);
        Enemy enemy = new Enemy(dungeon, 0, 0);
        Treasure treasure = new Treasure(0, 0);
        dungeon.addEntity(treasure);
        dungeon.addEntity(enemy);
        assertFalse("Incomplete", goal.isComplete());
        dungeon.removeEntity(treasure);
        assertTrue("Complete", goal.isComplete());
    }

    @Test
    public void checkCompositeGoal3(){
        // SwitchGoal AND (EnemyGoal OR TreasureGoal)
        Dungeon dungeon = new Dungeon(10, 10);
        CompositeGoal goal = new CompositeGoal("OR");
        Goal goal1 = new EnemyGoal(dungeon);
        Goal goal2 = new TreasureGoal(dungeon);
        goal.addGoal(goal1);
        goal.addGoal(goal2);
        CompositeGoal goal3 = new CompositeGoal("AND");
        Goal goal4 = new SwitchGoal(dungeon);
        goal3.addGoal(goal);
        goal3.addGoal(goal4);
        dungeon.setGoal(goal3);
        Enemy enemy = new Enemy(dungeon, 0, 0);
        Treasure treasure = new Treasure(0, 0);
        Switch dungeonSwitch = new Switch(0, 0);
        dungeon.addEntity(treasure);
        dungeon.addEntity(enemy);
        dungeon.addEntity(dungeonSwitch);
        assertFalse("Incomplete", goal3.isComplete());
        dungeon.removeEntity(treasure);
        assertFalse("Incomplete", goal3.isComplete());
        dungeonSwitch.setStatus(true);
        assertTrue("Complete", goal3.isComplete());
    }
}