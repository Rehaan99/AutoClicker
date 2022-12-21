package AutoClicker.View;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private final JButton buttonAutoClicker = new JButton("Start Auto Clicker (F1)");
    private final JTextField intervalACTextField = new JTextField();
    private final JTextField keyPressTextField = new JTextField();
    private final JTextField intervalKPTextField = new JTextField();
    private final JButton KPButton = new JButton("Start Auto Button");
    private final JButton settingsButton = new JButton();
    private final JPanel functionsPanel = new JPanel();

    public GUI() {
        ImageIcon settingsIcon = new ImageIcon("resources/settingsImage.png");
        Image image = settingsIcon.getImage();
        image = image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        settingsIcon = new ImageIcon(image);
        intervalACTextField.setText("1000");
        intervalKPTextField.setText("1000");
        JLabel intervalLabel = new JLabel("Auto Clicker:");
        JLabel keyPressLabel = new JLabel("Auto Button:");
        KPButton.setEnabled(false);
        settingsButton.setIcon(settingsIcon);
        settingsButton.setPreferredSize(new Dimension(10, 10));
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setOpaque(false);



        functionsPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        functionsPanel.setLayout(new GridLayout(0, 4));

        functionsPanel.add(new JLabel(""));
        functionsPanel.add(new JLabel("Interval (ms) 1000ms = 1s"));
        functionsPanel.add(new JLabel("Key Press"));
        functionsPanel.add(new JLabel("Start/Stop Button"));

        functionsPanel.add(intervalLabel);
        functionsPanel.add(intervalACTextField);
        functionsPanel.add(new JLabel(""));
        functionsPanel.add(buttonAutoClicker);
        functionsPanel.add(keyPressLabel);
        functionsPanel.add(intervalKPTextField);
        functionsPanel.add(keyPressTextField);
        functionsPanel.add(KPButton);

        JFrame GUIPanel = new JFrame();
        GUIPanel.setResizable(false);
        GUIPanel.add(settingsButton,BorderLayout.LINE_END);
        GUIPanel.add(functionsPanel, BorderLayout.CENTER);
        GUIPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIPanel.setTitle("Auto Clicker");
        GUIPanel.pack();
        GUIPanel.setVisible(true);
        GUIPanel.setAlwaysOnTop(true);
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
    public JButton getSettingsButton(){
        return settingsButton;
    }

    public JPanel getFunctionsPanel() {
        return functionsPanel;
    }
}