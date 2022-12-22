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
    private final JPanel settingsPanel = new JPanel();
    private final JTextField autoClickHotKeyStart = new JTextField();
    private final JFrame window = new JFrame();


    public GUI() {
        ImageIcon settingsIcon = new ImageIcon("resources/settingsImage.png");
        Image image = settingsIcon.getImage();
        image = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        settingsIcon = new ImageIcon(image);
        intervalACTextField.setText("1000");
        intervalKPTextField.setText("1000");
        JLabel intervalLabel = new JLabel("Auto Clicker:");
        JLabel keyPressLabel = new JLabel("Auto Button:");
        KPButton.setEnabled(false);

        autoClickHotKeyStart.setOpaque(false);
        autoClickHotKeyStart.setPreferredSize(new Dimension(25,25));

        settingsButton.setIcon(settingsIcon);
        settingsButton.setPreferredSize(new Dimension(25, 25));
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setOpaque(false);

        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));
        settingsPanel.setLayout(new GridLayout(0, 3));
        JLabel function = new JLabel("Function");
        function.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel hotKey = new JLabel("Hot Key");
        hotKey.setHorizontalAlignment(SwingConstants.LEFT);
        settingsPanel.add(function);
        settingsPanel.add(new JLabel(""));
        settingsPanel.add(hotKey);
        settingsPanel.add(new JLabel(""));
        settingsPanel.add(new JLabel(""));
        settingsPanel.add(new JLabel(""));
        settingsPanel.add(new JLabel("AC Start"));
        settingsPanel.add(new JLabel(""));
        settingsPanel.add(autoClickHotKeyStart);


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

        JPanel hotBar = new JPanel();
        hotBar.setLayout(new GridBagLayout());
        hotBar.add(settingsButton);

        window.setResizable(false);
        window.add(hotBar, BorderLayout.EAST);
        window.add(functionsPanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Auto Clicker");
        window.pack();
        window.setVisible(true);
        window.setAlwaysOnTop(true);
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

    public JPanel getSettingsPanel() {
        return settingsPanel;
    }
    public JPanel getFunctionsPanel() {
        return functionsPanel;
    }
    public JFrame getWindow(){
        return window;
    }
}