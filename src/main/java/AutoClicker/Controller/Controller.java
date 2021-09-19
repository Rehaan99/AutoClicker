package AutoClicker.Controller;

import AutoClicker.Model.Model;
import AutoClicker.View.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {

    private final Model model;
    public static boolean isClicking = false;
    private ExecutorService service = Executors.newFixedThreadPool(1);
    private final JButton button;

    public Controller(Model model, GUI gui) {
        this.model = model;
        button = gui.getButton();
        button.addActionListener(e -> clickConditions());
        button.getInputMap().put(KeyStroke.getKeyStroke("F1"), "AutoClick");
        button.getActionMap().put("AutoClick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickConditions();
            }
        });
    }

    public void clickConditions() {
        isClicking = !isClicking;
        model.setIsClicking(isClicking);
        if (isClicking) {
            button.setText("Stop Auto Clicker");
            service.execute(model);
        } else {
            button.setText("Start Auto Clicker");
            service.shutdownNow();
            service = Executors.newFixedThreadPool(1);
        }
    }

}
