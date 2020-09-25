package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.File;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;

    private Timeline time;

    private Boolean pause;
    /**
     * Constructor for the controller
     * @param dungeon
     * @param initialEntities
     */
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.time = new Timeline();
        this.pause = false;
        time.setCycleCount(Timeline.INDEFINITE);
        EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
                List<Opponents> enemies = dungeon.getEnemies();
                for(Opponents e : enemies){
                    e.patrol();
                }
			}
        };

        KeyFrame kf = new KeyFrame(Duration.millis(500),eh);
        time.getKeyFrames().addAll(kf);
    }
    /**
     * Initialize the basic dungeon layout
     */
    @FXML
    public void initialize() {
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);

        time.play();

    }
    /**
     * Handles key events, UP, DOWN, RIGHT, LEFT triggers player movement in respective directions
     * Holding SHIFT while moving allow player to push boulders
     * Press F to interact with entities
     * After every movement, the goal is checked to see if any is completed
     * @param event
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        if(this.pause == true){
            if(event.getCode() == KeyCode.ESCAPE){
                this.pause = false;
                time.play();
            }
        }else{
            switch (event.getCode()) {
                case UP:
                    if(event.isShiftDown()){
                        List<Entity> entities = dungeon.getEntity(player.getX(), player.getY()-1);
                        for(Entity b : entities){
                            if(b instanceof Boulder){
                                player.pushUp((Boulder) b);
                                dungeon.checkGoal();
                                break;
                            }
                        }
                    }
                    player.moveUp();
                    dungeon.checkGoal();
                    break;
                case DOWN:
                    if(event.isShiftDown()){
                        List<Entity> entities = dungeon.getEntity(player.getX(), player.getY()+1);
                        for(Entity b : entities){
                            if(b instanceof Boulder){
                                player.pushDown((Boulder) b);
                                dungeon.checkGoal();
                                break;
                            }
                        }
                    }
                    player.moveDown();
                    dungeon.checkGoal();
                    break;
                case LEFT:
                    if(event.isShiftDown()){
                        List<Entity> entities = dungeon.getEntity(player.getX()-1, player.getY());
                        for(Entity b : entities){
                            if(b instanceof Boulder){
                                player.pushLeft((Boulder) b);
                                dungeon.checkGoal();
                                break;
                            }
                        }
                    }
                    player.moveLeft();
                    dungeon.checkGoal();
                    break;
                case RIGHT:
                    if(event.isShiftDown()){
                        List<Entity> entities = dungeon.getEntity(player.getX()+1, player.getY());
                        for(Entity b : entities){
                            if(b instanceof Boulder){
                                player.pushRight((Boulder) b);
                                dungeon.checkGoal();
                                break;
                            }
                        }
                    }
                    player.moveRight();
                    dungeon.checkGoal();
                    break;
                case F:
                    List<Entity> entities = dungeon.getEntity(player.getX(), player.getY());
                    for(Entity e : entities){
                        if(e instanceof Pickable){
                            if(player.pickUp(e)){
                                dungeon.removeEntity(e);
                                e.remove();
                            }
                        }else if(e instanceof Portal){
                            Portal portal = dungeon.getPairPortal((Portal) e);
                            player.teleport(portal);
                        }
                    }
                    List<Door> doors = dungeon.getDoor(player.getX(), player.getY());
                    for(Door d : doors){
                        player.openDoor(d);
                    }
                    dungeon.checkGoal();
                    break;
                case ESCAPE:
                    this.pause = true;
                    time.pause();
                    break;
                default:
                    break;
                }
        }
        
    }

    public BooleanProperty getGoalProperty(){
        return dungeon.getGoalProperty();
    }

    public BooleanProperty isAlive(){
        return player.isAlive();
    }
}

