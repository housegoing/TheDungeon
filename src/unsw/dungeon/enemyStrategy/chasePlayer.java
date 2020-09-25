package unsw.dungeon.enemyStrategy;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Opponents;
//import unsw.dungeon.Player;

public class chasePlayer implements enemyStrategy{
    
    public void setDungeon(Dungeon dungeon){}
        
    @Override
    public void moveStrategy(Opponents enemy, Entity player){
        int p_x = player.getX();
        int p_y = player.getY();

        int e_x = enemy.getX();
        int e_y = enemy.getY();

        if(e_x > p_x && enemy.checkMovement("LEFT")){//if player is on the left
            enemy.moveLeft();
            enemy.setLastMovement("LEFT");
        }else if(e_x < p_x && enemy.checkMovement("RIGHT")){//if player is on the right
            enemy.moveRight();
            enemy.setLastMovement("RIGHT");
        }else if(e_y > p_y && enemy.checkMovement("UP")){//if player is above
            enemy.moveUp();
            enemy.setLastMovement("UP");
        }else if(e_y < p_y && enemy.checkMovement("DOWN")){//if player is below
            enemy.moveDown();
            enemy.setLastMovement("DOWN");
        }else if(e_x > p_x && enemy.checkMovement("LEFT") == false){
            if(e_x < p_x && enemy.checkMovement("RIGHT")){//if player is on the right
                enemy.moveRight();
                enemy.setLastMovement("RIGHT");
            }else if(e_y > p_y && enemy.checkMovement("UP")){//if player is above
                enemy.moveUp();
                enemy.setLastMovement("UP");
            }else if(e_y < p_y && enemy.checkMovement("DOWN")){//if player is below
                enemy.moveDown();
                enemy.setLastMovement("DOWN");
            }else if(enemy.checkMovement("RIGHT") && enemy.getLastMovement().equals("LEFT")==false){
                enemy.moveRight();
                enemy.setLastMovement("RIGHT");
            }else if(enemy.checkMovement("UP") && enemy.getLastMovement().equals("DOWN")==false){
                enemy.moveUp();
                enemy.setLastMovement("UP");
            }else if(enemy.checkMovement("DOWN") && enemy.getLastMovement().equals("UP")==false){
                enemy.moveDown();
                enemy.setLastMovement("DOWN");
            }
        }else if(e_x < p_x && enemy.checkMovement("RIGHT") == false){
            if(e_x > p_x && enemy.checkMovement("LEFT")){//if player is on the right
                enemy.moveLeft();
                enemy.setLastMovement("LEFT");
            }else if(e_y > p_y && enemy.checkMovement("UP")){//if player is above
                enemy.moveUp();
                enemy.setLastMovement("UP");
            }else if(e_y < p_y && enemy.checkMovement("DOWN")){//if player is below
                enemy.moveDown();
                enemy.setLastMovement("DOWN");
            }else if(enemy.checkMovement("LEFT") && enemy.getLastMovement().equals("RIGHT")==false){
                enemy.moveLeft();
                enemy.setLastMovement("LEFT");
            }else if(enemy.checkMovement("UP") && enemy.getLastMovement().equals("DOWN")==false){
                enemy.moveUp();
                enemy.setLastMovement("UP");
            }else if(enemy.checkMovement("DOWN")&& enemy.getLastMovement().equals("UP")==false){
                enemy.moveDown();
                enemy.setLastMovement("DOWN");
            }
        }else if(e_y > p_y && enemy.checkMovement("UP")==false){
            if(e_x > p_x && enemy.checkMovement("LEFT")){//if player is on the left
                enemy.moveLeft();
                enemy.setLastMovement("LEFT");
            }else if(e_x < p_x && enemy.checkMovement("RIGHT")){//if player is on the right
                enemy.moveRight();
                enemy.setLastMovement("RIGHT");
            }else if(e_y < p_y && enemy.checkMovement("DOWN")){//if player is below
                enemy.moveDown();
                enemy.setLastMovement("DOWN");
            }else if(enemy.checkMovement("LEFT") && enemy.getLastMovement().equals("RIGHT")==false){
                enemy.moveLeft();
                enemy.setLastMovement("LEFT");
            }else if(enemy.checkMovement("RIGHT") && enemy.getLastMovement().equals("LEFT")==false){
                enemy.moveRight();
                enemy.setLastMovement("RIGHT");
            }else if(enemy.checkMovement("DOWN")&& enemy.getLastMovement().equals("UP")==false){
                enemy.moveDown();
                enemy.setLastMovement("DOWN");
            }
        }else if(e_y < p_y && enemy.checkMovement("DOWN")==false){
            if(e_x > p_x && enemy.checkMovement("LEFT")){//if player is on the left
                enemy.moveLeft();
                enemy.setLastMovement("LEFT");
            }else if(e_x < p_x && enemy.checkMovement("RIGHT")){//if player is on the right
                enemy.moveRight();
                enemy.setLastMovement("RIGHT");
            }else if(e_y > p_y && enemy.checkMovement("UP")){//if player is below
                enemy.moveUp();
                enemy.setLastMovement("UP");
            }else if(enemy.checkMovement("LEFT") && enemy.getLastMovement().equals("RIGHT")==false){
                enemy.moveLeft();
                enemy.setLastMovement("LEFT");
            }else if(enemy.checkMovement("RIGHT") && enemy.getLastMovement().equals("LEFT")==false){
                enemy.moveRight();
                enemy.setLastMovement("RIGHT");
            }else if(enemy.checkMovement("UP")&& enemy.getLastMovement().equals("DOWN")==false){
                enemy.moveUp();
                enemy.setLastMovement("UP");
            }
        }
    }
}