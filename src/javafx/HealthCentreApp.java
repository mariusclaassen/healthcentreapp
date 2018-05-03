// Lecture 9: Health Centre App - Design of Interface

// PROBLEM STATEMENT:
// Design the graphical user interface of a Health Centre App

package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Marius Claassen
 */

public class HealthCentreApp extends Application {

    @Override public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("HealthCentreView.fxml"));
        primaryStage.setTitle("JavaFX Health Centre App");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
