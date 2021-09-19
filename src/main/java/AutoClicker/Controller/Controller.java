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
    private ExecutorService service = Executors.newFixedThreadPool(1);
    private final JButton button;
    private final JTextField intervalTextField;
    public Controller(Model model, GUI gui) {
        this.model = model;
        button = gui.getButton();
        intervalTextField =gui.getIntervalTextField();
        intervalTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)){
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER  || e.getKeyCode() == KeyEvent.VK_F1) {
                    intervalTextField.setFocusable(false);
                    clickConditions();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
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
        model.setIsClicking(isClicking,getInterval());
        if (isClicking) {
            button.setText("Stop Auto Clicker (F1)");
            intervalTextField.setFocusable(false);
            service.execute(model);
        } else {
            button.setText("Start Auto Clicker (F1)");
            intervalTextField.setFocusable(true);
            service.shutdownNow();
            service = Executors.newFixedThreadPool(1);
        }
    }
    public int getInterval(){
        int integer;
        try{
            integer = Integer.parseInt(intervalTextField.getText());
        }catch (NumberFormatException ex){
            integer =  300000;
        }
        if (integer > 300000){
            integer = 300000;
        }
        intervalTextField.setText(String.valueOf(integer));
        if (integer < 100){
            integer = 100;
        }
        return integer;
    }

}
