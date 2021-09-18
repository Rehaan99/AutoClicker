package AutoClicker.Controller;
import AutoClicker.Model.Model;
import AutoClicker.View.GUI;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    private final Model model;
    private final GUI gui;
    public static boolean isClicking = false;

    public Controller(Model model, GUI gui) {
        this.model = model;
        this.gui = gui;
    }


    public void run() {
        JButton button = gui.getButton();
        ExecutorService service = Executors.newFixedThreadPool(1);
        button.addActionListener(e -> {
        isClicking = !isClicking;
        model.setIsClicking(isClicking);
        service.execute(model);
        if (isClicking){
            button.setText("Stop AutoClicker");
        }
        else{
            button.setText("Start AutoClicker");

        }
    });
    }
}
