package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginFXML.fxml"));
        Application.setUserAgentStylesheet("/css/cupertino-dark.css");
        stage.initStyle(StageStyle.DECORATED);
        stage.setMaximized(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(489);
        stage.setMinHeight(585);
        stage.initStyle(StageStyle.DECORATED.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
