package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Heart extends Entity{

    private BooleanProperty visible;
    /**
     * Constructor initialize key instance on position (x,y)
     * @param x
     * @param y
     */
    public Heart(int x, int y){
        super(x, y);
        this.visible = new SimpleBooleanProperty(true);
    }

    public BooleanProperty getProperty(){
        return visible;
    }
    /**
     * Heart disappears on UI if visible is false
     * @param val
     */
    public void setVisible(boolean val){
        visible.setValue(val);
    }
}