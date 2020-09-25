package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Door extends Entity {

    private BooleanProperty status;
    private int id;
    /**
     * Constructor for initializing the door instance on position (x,y)
     * @param x
     * @param y
     * @param id
     */
    public Door(int x, int y, int id){
        super(x, y);
        this.status = new SimpleBooleanProperty(false);
        this.id = id;
    }
    /**
     * Check if the provided key matches this door
     * @param keyid
     * @return
     */
    public boolean checkKey(int keyid){
        if(id == keyid){
            open();
            return true;
        }else{
            return false;
        }
    }

    public BooleanProperty getStatus(){
        return this.status;
    }

    public boolean isOpen(){
        return status.getValue();
    }
    /**
     * Open the door
     */
    public void open(){
        status.setValue(true);
    }

}