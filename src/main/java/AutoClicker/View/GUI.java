package AutoClicker.View;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private final JFrame frame = new JFrame();
    private final JButton buttonAutoClicker = new JButton("Start Auto Clicker");

    public GUI() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(buttonAutoClicker);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Auto Clicker");
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }

    public JButton getButton() {
        return buttonAutoClicker;
    }

}