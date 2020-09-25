package unsw.dungeon.Goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;

public class TreasureGoal implements Goal{
    
    private BooleanProperty completed;
    private Dungeon dungeon;

    /**
     * Constructor for initializing the treasure goal
     * @param dungeon
     */
    public TreasureGoal(Dungeon dungeon){
        this.dungeon = dungeon;
        this.completed = new SimpleBooleanProperty(false);
    }
    /**
     * If there are no more treasures in dungeon, the goal is complete
     */
    @Override
    public void updateStatus() {
        if(dungeon.getTreasureCount() == 0){
            System.out.println("Treasure completed");
            this.completed.setValue(true);
        }
    }
    /**
     * Try to update the status and check if it is complete
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