package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty existance;
    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.existance = new SimpleBooleanProperty(true);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public void setX(int x){
        this.x.setValue(x);
    }

    public void setY(int y){
        this.y.setValue(y);
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }

    public BooleanProperty exist(){
        return this.existance;
    }
    // Remove entity from UI
    public void remove(){
        this.existance.setValue(false);
    }
    // Show entity on UI
    public void show(){
        this.existance.setValue(true);
    }
}
