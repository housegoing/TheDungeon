package unsw.dungeon;

import java.io.File;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameOverController {

    @FXML
    private Button retry;
    @FXML
    private Button menu;
    @FXML
    private Button exit;
    @FXML
    private Pane pane;

    private Stage stage;
    /**
     * Constructor for the controller
     * @param stage
     */
    public GameOverController(Stage stage) {
        this.stage = stage;
    }
    /**
     * Display game window when player wants to retry
     * Add relevent listeners to display gameover window or victory window
     * @throws IOException
     */
    @FXML
    public void handleRetry() throws IOException {
        stage.setTitle("Dungeon");

        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader("SelfMade.json");

        DungeonController controller = dungeonLoader.loadController();

        controller.getGoalProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldBoolean, Boolean newBoolean) {
                if(newBoolean == true){
                    try {
                        victory();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        controller.isAlive().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldBoolean, Boolean newBoolean) {
				if(newBoolean == false){
                    try {
                        gameOver();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
			}
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Return the menu
     * @throws IOException
     */
    @FXML
    public void handleMenu() throws IOException {
        stage.setTitle("Menu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
        MenuController menuController = new MenuController(stage);
        loader.setController(menuController);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        menuController.setBackground();
        stage.show();
    }
    /**
     * Exit the game
     */
    @FXML
    public void handleExit() {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }
    /**
     * Reshow gameover window if player failed in retry
     */
    protected void gameOver() throws IOException {
        stage.setTitle("GameOver");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        setBackground();
        stage.show();
    }
    /**
     * Show victory window if player succeed in retry
     * @throws IOException
     */
    protected void victory() throws IOException {
        stage.setTitle("Victory");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VictoryView.fxml"));
        VictoryController victoryController = new VictoryController(stage);
        loader.setController(victoryController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        victoryController.setBackground();
        stage.show();
    }
    /**
     * Set background for gameover window
     */
    public void setBackground(){
        File file = new File(System.getProperty("user.dir").toString() + "/images/gameOver.png");
		BackgroundImage background = new BackgroundImage(new Image(file.toURI().toString()), BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background b = new Background(background);
		pane.setBackground(b);
    }
}