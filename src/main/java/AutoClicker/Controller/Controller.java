package AutoClicker.Controller;
import AutoClicker.Model.Model;
import AutoClicker.View.GUI;

import javax.swing.*;

public class Controller implements Runnable {
    private final Model model;
    private final GUI gui;
    public static boolean isClicking = false;

    public Controller(Model model, GUI gui) {
        this.model = model;
        this.gui = gui;
    }

    @Override
    public void run() {
        JButton button = gui.getButton();
        button.addActionListener(e -> {
        isClicking = !isClicking;
        if (isClicking){
            button.setText("Stop AutoClicker");
        }
        else{
            button.setText("Start AutoClicker");
        }
        model.buttonClickEvent(isClicking);
    });
    }


}
