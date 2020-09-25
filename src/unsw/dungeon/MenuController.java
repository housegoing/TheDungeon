package unsw.dungeon;

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

import java.io.File;
import java.io.IOException;

public class MenuController {
    @FXML
    private Button play;

    @FXML
    private Button exit;

    @FXML
    private Pane pane;
    
    private final Stage stage;
    /**
     * Constructor for the controller
     * @param stage
     */
    public MenuController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void handlePlay() throws IOException {
        launch();
    }
    /**
     * Load the dungeon maps and assign listeners to the dungeon status
     * to display gameover window or victory window
     * @throws IOException
     */
    public void launch() throws IOException {
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
                    try{
                        gameOver();
                    }catch (IOException e) {
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
     * Exit the menu
     * @throws IOException
     */
    @FXML
    public void handleExit() throws IOException {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }
    /**
     * Display gameover window
     * @throws IOException
     */
    public void gameOver() throws IOException {
        stage.setTitle("GameOver");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameOverView.fxml"));
        GameOverController gameOverController = new GameOverController(stage);
        
        loader.setController(gameOverController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        gameOverController.setBackground();
        stage.show();
    }
    /**
     * Display victory window
     * @throws IOException
     */
    public void victory() throws IOException {
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
     * Set the background for the menu
     */
    public void setBackground(){
        File file = new File(System.getProperty("user.dir").toString() + "/images/MenuBackground.jpeg");
		BackgroundImage background = new BackgroundImage(new Image(file.toURI().toString()), BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background b = new Background(background);
		pane.setBackground(b);
    }
}