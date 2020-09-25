package unsw.dungeon.PlayerState;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.InvincibilityPotion;
import unsw.dungeon.Opponents;
import unsw.dungeon.Player;

public class InvincibleState implements PlayerState {

    private Dungeon dungeon;
    private Player player;
    private InvincibilityPotion invincibilityPotion;
    private static int duration = 0;
    private Timer timer = new Timer();
    private TimerTask timerTask; 
    /**
     * Constructor for initializing the invincible state for player
     * @param dungeon
     * @param player
     * @param invincibilityPotion
     */
    public InvincibleState(Dungeon dungeon, Player player, InvincibilityPotion invincibilityPotion) {
        //super(x, y);
        this.dungeon = dungeon;
        this.player = player;
        this.invincibilityPotion = invincibilityPotion;
        InvincibleState.duration = this.invincibilityPotion.getDuration();

        start();
    }

    private void start() {
        timerTask = new TimerTask(){
            public void run(){
                duration -= 1000;
                if(duration == 0){
                    timerTask.cancel();
                    timer.cancel();
                    changeState();
                }
            }
            private void changeState() {
                if(player.getSwordUses() > 0){
                    player.changeToHasSword();
                }else{
                    player.changeToNormal();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void addDuration(int duration){
        InvincibleState.duration += duration;
    }
    /**
     * Player with in invincible state kills enemy upon collision
     */
    @Override
    public void collideWithEnemy() {
        int x = player.getX();
        int y = player.getY();
        List<Entity> entities = dungeon.getEntity(x, y);
        if(entities.size() == 1){ return; }
        for(Entity e : entities){
            if(e instanceof Opponents){
                ((Opponents) e).death(e);
            }
        }
    }
    
}