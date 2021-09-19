package AutoClicker.View;

import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private final JButton buttonAutoClicker = new JButton("Start Auto Clicker (F1)");
    private final JTextField intervalTextField = new JTextField();
    public GUI() {
        JPanel panel = new JPanel();
        intervalTextField.setText("0");
        JLabel intervalLabel = new JLabel("Enter Clicking interval in ms (1000ms = 1s)");
        panel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(intervalLabel);
        panel.add(intervalTextField);
        panel.add(buttonAutoClicker);
        JFrame frame = new JFrame();
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Auto Clicker");
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

    }
    public JTextField getIntervalTextField(){
        return intervalTextField;
    }

    public JButton getButton() {
        return buttonAutoClicker;
    }

    public void nativeKeyPressed(NativeKeyListener nativeKeyListener) {

    }
}