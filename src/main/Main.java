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

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Medical office management");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
