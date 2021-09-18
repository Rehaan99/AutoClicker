package AutoClicker.Controller;
import AutoClicker.Model.Model;
import AutoClicker.View.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    private final Model model;
    public static boolean isClicking = false;
    private final ExecutorService service = Executors.newFixedThreadPool(1);
    private final JButton button;
    public Controller(Model model, GUI gui) {
        this.model = model;
        button = gui.getButton();
       JFrame frame = gui.getJFrame();

        button.addActionListener(e -> {
            clickConditions();

        });
        button.getInputMap().put(KeyStroke.getKeyStroke("F1"), "AutoClick");
        button.getActionMap().put("AutoClick", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickConditions();
            }
        });


    }
    public void clickConditions(){
        isClicking = !isClicking;
        model.setIsClicking(isClicking);
        service.execute(model);
        if (isClicking){
            button.setText("Stop AutoClicker");
        }
        else{
            button.setText("Start AutoClicker");

        }
    }
}
