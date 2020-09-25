package unsw.dungeon;

import java.io.File;
import java.io.IOException;

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

public class VictoryController {
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
    public VictoryController(Stage stage) {
        this.stage = stage;
    }
    /**
     * Return to menu
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
     * Exit the window
     */
    @FXML
    public void handleExit() {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }
    /**
     * Set background for victory window
     */
    public void setBackground(){
        File file = new File(System.getProperty("user.dir").toString() + "/images/Victory.png");
		BackgroundImage background = new BackgroundImage(new Image(file.toURI().toString()), BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background b = new Background(background);
		pane.setBackground(b);
    }
}