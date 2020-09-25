package unsw.dungeon.Goals;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CompositeGoal implements Goal {

    private BooleanProperty completed;
    private List<Goal> goals;
    private String logic;
    /**
     * Constructor for initializing the composite goal
     * @param string
     */
    public CompositeGoal(String string){
        this.completed = new SimpleBooleanProperty(false);
        this.goals = new ArrayList<Goal>();
        this.logic = string;
    }
    /**
     * Add a goal to the composite array list
     * @param goal
     */
    public void addGoal(Goal goal){
        this.goals.add(goal);
    }

    public void removeGoal(Goal goal){
        this.goals.remove(goal);
    }
    /**
     * Check if the composite goals are completed
     */
    @Override
    public void updateStatus() {
        boolean temp = true;

        if(logic.equals("AND")){
            if(goals != null || goals.size() != 0){
                for(Goal g : goals){
                    temp = temp && g.isComplete();
                }
            }
        }else{  
            temp = false;
            if(goals != null || goals.size() != 0){
                for(Goal g : goals){
                    temp = temp || g.isComplete();
                }
            }
        }

        this.completed.setValue(temp);
    }
    /**
     * Update the status and check if goal is complete
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