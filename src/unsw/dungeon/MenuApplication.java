package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuApplication extends Application {
    /**
     * Intitiate the menu
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Menu");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
        MenuController menuController = new MenuController(primaryStage);
        loader.setController(menuController);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        menuController.setBackground();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
