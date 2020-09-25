package unsw.dungeon.Goals;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Exit;

public class ExitGoal implements Goal{

    private BooleanProperty completed;
    private Dungeon dungeon;
    /**
     * Constructor for initializing the exit goal
     * @param dungeon
     */
    public ExitGoal(Dungeon dungeon){
        //this.dungeon = dungeon;
        this.completed = new SimpleBooleanProperty(false);
        this.dungeon = dungeon;
    }
    /**
     * Check if player is on position of exit, if so then goal is complete
     */
    @Override
    public void updateStatus() {
        List<Entity> entities = dungeon.getEntity();
        for(Entity e : entities){
            if(e instanceof Exit){
                if(e.getX() == dungeon.getPlayerX() && e.getY() == dungeon.getPlayerY()){
                    completed.setValue(true);
                }
            }
        }
    }
    /**
     * Update the status and check if goal is complete
     * @return
     */
    @Override
    public boolean isComplete() {
        updateStatus();
        return this.completed.getValue();
    }

    @Override
    public BooleanProperty getProperty() {
        return this.completed;
    }
}