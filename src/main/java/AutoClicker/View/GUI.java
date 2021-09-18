package AutoClicker.View;
import javax.swing.*;
import java.awt.*;

public class GUI {

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private final JButton buttonAutoClicker = new JButton("Start Auto Clicker");
    public GUI() {
        panel.setBorder(BorderFactory.createEmptyBorder(100,300,100,300));
        panel.setLayout(new GridLayout(0,1));
        panel.add(buttonAutoClicker);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Auto Clicker");
        frame.pack();
        frame.setVisible(true);
    }


    public JButton getButton() {
        return buttonAutoClicker;
    }
}
