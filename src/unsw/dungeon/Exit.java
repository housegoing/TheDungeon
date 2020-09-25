package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Exit extends Entity {

    private BooleanProperty status;
    /**
     * Constructor initialize exit instance on position (x,y)
     * @param x
     * @param y
     */
    public Exit(int x, int y) {
        super(x, y);
        this.status = new SimpleBooleanProperty(false);
    }

    public boolean getStatus(){
        return this.status.getValue();
    }

    public void setStatus(boolean status){
        this.status.setValue(status);
    }
}