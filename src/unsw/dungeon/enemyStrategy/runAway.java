package unsw.dungeon.enemyStrategy;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Opponents;
//import unsw.dungeon.Player;

public class runAway implements enemyStrategy{
    
    public void setDungeon(Dungeon dungeon){}
    
    @Override
    public void moveStrategy(Opponents enemy, Entity player){
        int p_x = player.getX();
        int p_y = player.getY();

        int e_x = enemy.getX();
        int e_y = enemy.getY();
        
        if(e_x > p_x && enemy.checkMovement("RIGHT")){//if player is on the left
            enemy.moveRight();
        }else if(e_x < p_x && enemy.checkMovement("LEFT")){//if player is on the right
            enemy.moveLeft();
        }else if(e_y > p_y && enemy.checkMovement("DOWN")){//if player is above
            enemy.moveDown();
        }else if(e_y < p_y && enemy.checkMovement("UP")){//if player is below
            enemy.moveUp();
        }else if(e_x > p_x && enemy.checkMovement("RIGHT")==false){
            if(e_x < p_x && enemy.checkMovement("LEFT")){
                enemy.moveLeft();
            }else if(e_y > p_y && enemy.checkMovement("DOWN")){
                enemy.moveDown();
            }else if(e_y < p_y && enemy.checkMovement("UP")){
                enemy.moveUp();
            }else if(enemy.checkMovement("LEFT")){
                enemy.moveLeft();
            }else if(enemy.checkMovement("DOWN")){
                enemy.moveDown();
            }else if(enemy.checkMovement("UP")){
                enemy.moveUp();
            }
        }else if(e_x < p_x && enemy.checkMovement("LEFT")==false){
            if(e_x > p_x && enemy.checkMovement("RIGHT")){
                enemy.moveRight();
            }else if(e_y > p_y && enemy.checkMovement("DOWN")){
                enemy.moveDown();
            }else if(e_y < p_y && enemy.checkMovement("UP")){
                enemy.moveUp();
            }else if(enemy.checkMovement("RIGHT")){
                enemy.moveRight();
            }else if(enemy.checkMovement("DOWN")){
                enemy.moveDown();
            }else if(enemy.checkMovement("UP")){
                enemy.moveUp();
            }
        }else if(e_y > p_y && enemy.checkMovement("DOWN")==false){
            if(e_x > p_x && enemy.checkMovement("RIGHT")){
                enemy.moveRight();
            }else if(e_x < p_x && enemy.checkMovement("LEFT")){
                enemy.moveLeft();
            }else if(e_y < p_y && enemy.checkMovement("UP")){
                enemy.moveUp();
            }else if(enemy.checkMovement("RIGHT")){
                enemy.moveRight();
            }else if(enemy.checkMovement("LEFT")){
                enemy.moveLeft();
            }else if(enemy.checkMovement("UP")){
                enemy.moveUp();
            }
        }else if(e_y < p_y && enemy.checkMovement("UP")==false){
            if(e_x > p_x && enemy.checkMovement("RIGHT")){
                enemy.moveRight();
            }else if(e_x < p_x && enemy.checkMovement("LEFT")){
                enemy.moveLeft();
            }else if(e_y > p_y && enemy.checkMovement("DOWN")){
                enemy.moveDown();
            }else if(enemy.checkMovement("RIGHT")){
                enemy.moveRight();
            }else if(enemy.checkMovement("LEFT")){
                enemy.moveLeft();
            }else if(enemy.checkMovement("DOWN")){
                enemy.moveDown();
            }
        }
    }
}