package AutoClicker.Controller;

import AutoClicker.Model.*;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;


public class Controller implements Initializable, Observer {

    public static List<Observer> observerList = new ArrayList<>();
    private KeyPress keyPress;
    private boolean isClicking = false;
    private boolean isPressing = false;
    private static final int maxFunctions = 999999999; // this will run for almost 8 years if set to the fastest interval (basically infinite for our purposes)


    @FXML
    private Button clickStartButton;
    @FXML
    private TextField clickInterval;
    @FXML
    private CheckBox clickCheckMax;
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private Label clickLabel;
    @FXML
    private TextField clickMax;
    @FXML
    private TextField pressInterval;
    @FXML
    private TextField pressKey;
    @FXML
    private CheckBox pressCheckMax;
    @FXML
    private TextField pressMax;
    @FXML
    private Button PressStartButton;
    @FXML
    private Button pressGeneratorButton;
    @FXML
    private Label pressLabel;
    @FXML
    private Button clickGeneratorButton;

    private AutoClick autoClick;

    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    private void notifyObservers(String function) {
        for (Observer observer : observerList) {
            if(!observer.toString().contains("AutoClicker.Controller.Controller")) {
                observer.update(function);
            }
        }
    }

    @Override
    public void update(String function) {
        switch (function) {
            case "click" -> {
                if (!clickCheckMax.isSelected() || Objects.equals(clickMax.getText(), "")) {
                    doFunction(autoClick, getInterval(clickInterval), maxFunctions);
                } else {
                    doFunction(autoClick, getInterval(clickInterval), Integer.parseInt(clickMax.getText()));
                }
            }
            case "press" -> {
                keyPress.setKeyCode(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(pressKey.getText().charAt(0)));
                if (!pressCheckMax.isSelected() || Objects.equals(pressMax.getText(), "")) {
                    doFunction(keyPress, getInterval(pressInterval), maxFunctions);
                } else {
                    doFunction(keyPress, getInterval(pressInterval), Integer.parseInt(pressMax.getText()));
                }
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        autoClick = new AutoClick();
        keyPress = new KeyPress();
        clickInterval.setText("1000");
        pressInterval.setText("1000");

        addFormatter(clickInterval);
        addFormatter(pressInterval);
        addFormatter(clickMax);
        addFormatter(pressMax);

        clickCheckMax.setOnAction(actionEvent -> {
            clickMax.setDisable(!clickCheckMax.isSelected());
        });

        pressCheckMax.setOnAction(actionEvent -> {
            pressMax.setDisable(!pressCheckMax.isSelected());
        });

        addObserver(this);

        clickStartButton.setOnAction(e -> {
            notifyObservers("click");
            if (!clickCheckMax.isSelected() || Objects.equals(clickMax.getText(), "")) {
                doFunction(autoClick, getInterval(clickInterval), maxFunctions);
            } else {
                doFunction(autoClick, getInterval(clickInterval), Integer.parseInt(clickMax.getText()));
            }

        });
        PressStartButton.setOnAction(e -> {
            notifyObservers("press");
            keyPress.setKeyCode(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(pressKey.getText().charAt(0)));
            if (!pressCheckMax.isSelected() || Objects.equals(pressMax.getText(), "")) {
                doFunction(keyPress, getInterval(pressInterval), maxFunctions);
            } else {
                doFunction(keyPress, getInterval(pressInterval), Integer.parseInt(pressMax.getText()));
            }
        });

        pressKey.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().isDigitKey() || keyEvent.getCode().isLetterKey() || keyEvent.getCode() == KeyCode.SPACE) {
                if(PressStartButton.isDisabled()) {
                    PressStartButton.setDisable(false);
                    notifyObservers("pressDisabled");
                }
                if (pressKey.getText().length() > 0) {
                    pressKey.setText("");
                }
                if (keyEvent.getCode() == KeyCode.SPACE) {
                    pressKey.setText("[SPACE]");
                }
            } else {
                if(!PressStartButton.isDisabled()) {
                    PressStartButton.setDisable(true);
                    notifyObservers("pressDisabled");
                }
                pressKey.setText("");
            }
        });

        AutoClick.addSwingWorkerListener(new SwingWorkerListener() {
            @Override
            public void onSwingWorkerDone(SwingWorker<?, ?> worker) {
                Platform.runLater(() -> clickStartButton.setText("Start Clicker (F1)"));

                isClicking = false;
                clickInterval.setDisable(false);
                clickCheckMax.setDisable(false);
                if (clickCheckMax.isSelected()) {
                    clickMax.setDisable(false);
                }
            }

            @Override
            public void onSwingWorkerStart(SwingWorker<?, ?> worker) {
                Platform.runLater(() -> clickStartButton.setText("Stop Clicker (F2)"));
                isClicking = true;
                clickInterval.setDisable(true);
                clickCheckMax.setDisable(true);
                clickMax.setDisable(true);
            }
        });

        KeyPress.addSwingWorkerListener(new SwingWorkerListener() {
            @Override
            public void onSwingWorkerDone(SwingWorker<?, ?> worker) {
                Platform.runLater(() -> PressStartButton.setText("Start Key Press"));
                isPressing = false;
                setFocusable();
            }

            @Override
            public void onSwingWorkerStart(SwingWorker<?, ?> worker) {
                Platform.runLater(() -> PressStartButton.setText("Stop Key Press"));
                isPressing = true;
                setFocusable();
            }
        });

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_F1 && !isClicking) {
                    doFunction(autoClick, getInterval(clickInterval), maxFunctions);
                } else if (e.getKeyCode() == NativeKeyEvent.VC_F2 && isClicking) {
                    doFunction(autoClick, getInterval(clickInterval), maxFunctions);
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            }
        });

    }

    private void addFormatter(TextField intervalField) {
        UnaryOperator<TextFormatter.Change> filter = change -> change.getText().matches("[0-9]*") ? change : null;
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        intervalField.setTextFormatter(textFormatter);
    }

    private void setFocusable() {
        pressInterval.setDisable(isPressing);
        pressKey.setDisable(isPressing);
        pressCheckMax.setDisable(isPressing);
        if (pressCheckMax.isSelected()) {
            pressMax.setDisable(isPressing);
        }
    }

    private void doFunction(AutoFunction function, int interval, int maxFunctions) {
        if (function.getWorker() == null || function.getWorker().isDone()) {
            function.start(interval, maxFunctions);
        } else {
            function.getWorker().cancel(true);
        }
    }

    private int getInterval(TextField field) {
        int interval;
        try {
            interval = Integer.parseInt(field.getText());
        } catch (NumberFormatException ex) {
            interval = 300000;
        }
        if (interval > 300000) {
            interval = 300000;
        } else if (interval < 250) {
            interval = 250;
        }
        field.setText(String.valueOf(interval));
        return interval;
    }

}
