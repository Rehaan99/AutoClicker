package AutoClicker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);

    }
    @Override
    public void start(Stage stage) throws Exception {
        Scene autoFunctions = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scenebuilder.fxml"))));
        autoFunctions.getStylesheets().add(Objects.requireNonNull(getClass().getResource("autoStyle.css")).toExternalForm());
        stage.setTitle("Super Cool Automatic Functions");
        stage.setScene(autoFunctions);
        stage.setAlwaysOnTop(true);
        stage.show();
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        Scene overlay = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("displayRunning.fxml"))));
        overlay.getStylesheets().add(Objects.requireNonNull(getClass().getResource("overlay.css")).toExternalForm());
        Stage stage2 = new Stage();
        stage2.setScene(overlay);
        stage2.initStyle(StageStyle.UNDECORATED);
        stage2.setAlwaysOnTop(true);
        stage2.show();
    }
}