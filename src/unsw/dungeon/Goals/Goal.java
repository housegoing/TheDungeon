package unsw.dungeon.Goals;

import javafx.beans.property.BooleanProperty;

public interface Goal {

    public void updateStatus();
    public boolean isComplete();
    public BooleanProperty getProperty();
    //public void addGoal(Goal goal);
}