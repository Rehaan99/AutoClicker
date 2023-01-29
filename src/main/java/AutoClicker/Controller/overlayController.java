package AutoClicker.Controller;

import AutoClicker.Model.Observer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class overlayController implements Observer, Initializable {

    @FXML
    private Button clickerActiveOverlay;
    @FXML
    private Button PressActiveOverlay;
    private boolean isClicking = false;
    private boolean isPressing = false;

    public void addObserver(Observer observer) {
        Controller.observerList.add(observer);
    }

    private void notifyObservers(String function) {
        for (Observer observer : Controller.observerList) {
            if (!observer.toString().contains("overlayController")) {
                observer.update(function);
            }
        }
    }

    @Override
    public void update(String function) {
        switch (function) {
            case "click" -> {
                isClicking = !isClicking;
                updateButton(clickerActiveOverlay, isClicking);
            }
            case "press" -> {
                isPressing = !isPressing;
                updateButton(PressActiveOverlay, isPressing);
            }
            case "pressDisabled" -> PressActiveOverlay.setDisable(!PressActiveOverlay.isDisabled());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addObserver(this);

        clickerActiveOverlay.setOnAction(e -> {
            isClicking = !isClicking;
            updateButton(clickerActiveOverlay, isClicking);
            notifyObservers("click");
        });

        PressActiveOverlay.setOnAction(e -> {
            isPressing = !isPressing;
            updateButton(PressActiveOverlay, isPressing);
            notifyObservers("press");
        });
    }

    private void updateButton(Button button, boolean isFunctioning) {
        if (isFunctioning) {
            button.setStyle("-fx-background-color: green;");
        } else {
            button.setStyle("-fx-background-color: red;");
        }

    }
}
