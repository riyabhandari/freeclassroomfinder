package SplashScreen;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SplashScreenController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    AnimationGenerator animationGenerator = null;
    @FXML
    private AnchorPane parent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/Login/Login.fxml"));

            makeStageDrageable();
            animationGenerator = new AnimationGenerator();
            animationGenerator.applyFadeAnimationOn01(parent, 0, 1, 0.2, 1, (e) -> {

                animationGenerator.applyFadeAnimationOn02(parent, 3500, 0.2, 1, 1, (e2) -> {
                    parent.getChildren().removeAll();
                    parent.getChildren().setAll(fxml);
                });
            });
        } catch (IOException ex) {
            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makeStageDrageable() {
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.primaryStage.setX(event.getScreenX() - xOffset);
                Main.primaryStage.setY(event.getScreenY() - yOffset);
                Main.primaryStage.setOpacity(0.7f);
            }
        });
        parent.setOnDragDone((e) -> {
            Main.primaryStage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((e) -> {
            Main.primaryStage.setOpacity(1.0f);
        });
    }

}
