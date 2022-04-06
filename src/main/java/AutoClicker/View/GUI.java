package AutoClicker.View;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private final JButton buttonAutoClicker = new JButton("Start Auto Clicker (F1)");
    private final JTextField intervalACTextField = new JTextField();
    private final JTextField keyPressTextField = new JTextField();
    private final JTextField intervalKPTextField = new JTextField();
    private final JButton KPButton = new JButton("Start Auto Button");

    public GUI() {

        JPanel panel = new JPanel();
        intervalACTextField.setText("1000");
        intervalKPTextField.setText("1000");
        JLabel intervalLabel = new JLabel("Auto Clicker:");
        JLabel keyPressLabel = new JLabel("Auto Button:");
        KPButton.setEnabled(false);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.setLayout(new GridLayout(0, 4));

        panel.add(new JLabel(""));
        panel.add(new JLabel("Interval (ms) 1000ms = 1s"));
        panel.add(new JLabel("Key Press"));
        panel.add(new JLabel("Start/Stop Button"));

        panel.add(intervalLabel);
        panel.add(intervalACTextField);
        panel.add(new JLabel(""));
        panel.add(buttonAutoClicker);


        panel.add(keyPressLabel);
        panel.add(intervalKPTextField);
        panel.add(keyPressTextField);
        panel.add(KPButton);

        JFrame frame = new JFrame();
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Auto Clicker");
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

    }
    public JTextField getKeyPressTextField(){
        return keyPressTextField;
    }
    public JTextField getIntervalKPTextField(){
        return intervalKPTextField;
    }
    public JTextField getIntervalACTextField(){
        return intervalACTextField;
    }
    public JButton getKPButton(){
        return KPButton;
    }
    public JButton getACButton(){
        return buttonAutoClicker;
    }
}