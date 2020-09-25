package unsw.dungeon.Goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;

public class SwitchGoal implements Goal{

    private BooleanProperty completed;
    private Dungeon dungeon;
    /**
     * Constructor for initializing the switch goal
     * @param dungeon
     */
    public SwitchGoal(Dungeon dungeon){
        this.dungeon = dungeon;
        this.completed = new SimpleBooleanProperty(false);
    }
    /**
     * If all switch are activate, the goal is complete
     * Otherwise if the boulder was moved away and deactivate the switch after goal is complete
     * Set the goal back to incomplete
     */
    @Override
    public void updateStatus() {
        if(dungeon.getUnactivedSwitchCount() == 0){
            this.completed.setValue(true);
        }else{
            this.completed.setValue(false);
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