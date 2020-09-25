package unsw.dungeon.Goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import unsw.dungeon.Dungeon;

public class EnemyGoal implements Goal {

    private BooleanProperty completed;
    private Dungeon dungeon;
    /**
     * Constructor for initializing the enemy goal
     * @param dungeon
     */
    public EnemyGoal(Dungeon dungeon){
        this.dungeon = dungeon;
        this.completed = new SimpleBooleanProperty(false);
    }
    /**
     * If there are no enemy left in dungeon, the goal is complete
     */
    @Override
    public void updateStatus(){
        //System.out.println("count" + dungeon.getEnemyCount());
        if(dungeon.getEnemyCount() == 0){
            System.out.println("Enemy completed");
            this.completed.setValue(true);
        }
    }
    /**
     * Update the status and check if goal is complete
     * @return
     */
    @Override
    public boolean isComplete() {
        updateStatus();
        return completed.getValue();
    }

    @Override
    public BooleanProperty getProperty() {
        return this.completed;
    }
}