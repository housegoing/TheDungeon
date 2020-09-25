package unsw.dungeon.PlayerState;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Player;

public class ImmuneState implements PlayerState {

    private Dungeon dungeon;
    private Player player;
    private static int duration = 0;
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask(){
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
    /**
     * Constructor for initializing the invincible state for player
     * @param dungeon
     * @param player
     */
    public ImmuneState(Dungeon dungeon, Player player) {
        //super(x, y);
        this.dungeon = dungeon;
        this.player = player;
        ImmuneState.duration = 3000;

        start();
    }

    private void start() {
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void addDuration(int duration){
        ImmuneState.duration += duration;
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
    }
    
}