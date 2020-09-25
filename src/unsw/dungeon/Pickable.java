package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Pickable extends Entity{
    private BooleanProperty status;//true means item exists
    public Pickable(int x, int y){
        super(x, y);
        this.status = new SimpleBooleanProperty(true);
    }
    public void picked(){
        status.setValue(false);
    }
}