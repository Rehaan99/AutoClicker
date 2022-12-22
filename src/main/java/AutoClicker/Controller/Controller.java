package AutoClicker.Controller;

import AutoClicker.Model.AutoClick;
import AutoClicker.Model.KeyPress;
import AutoClicker.View.GUI;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Controller {

    private final AutoClick autoClick;
    private final KeyPress keyPress;

    public boolean isClicking = false;
    public boolean isPressing = false;
    private final JButton ACButton;
    private final JTextField intervalACTextField;
    private final JButton KPButton;
    private final JTextField intervalKPTextField;
    private final JTextField keyPressTextField;
    private final JButton settingsButton;
    private final JPanel functionsPanel;
    private final JPanel settingsPanel;
    private final JFrame window;
    public boolean isVisible = true;

    public Controller(AutoClick autoClick, GUI gui, KeyPress keyPress) throws NativeHookException {
        this.autoClick = autoClick;
        this.keyPress = keyPress;
        window = gui.getWindow();
        functionsPanel = gui.getFunctionsPanel();
        settingsPanel = gui.getSettingsPanel();
        ACButton = gui.getACButton();
        intervalACTextField = gui.getIntervalACTextField();
        intervalKPTextField = gui.getIntervalKPTextField();
        keyPressTextField = gui.getKeyPressTextField();
        KPButton = gui.getKPButton();
        settingsButton = gui.getSettingsButton();
        keyPresser();
        autoClicker();
        settings();
    }

    public void settings() {
        settingsButton.addActionListener(e -> {
            isVisible = !isVisible;
            if(isVisible){
                window.remove(settingsPanel);
                window.add(functionsPanel, BorderLayout.CENTER);
            }else {
                window.remove(functionsPanel);
                window.add(settingsPanel, BorderLayout.CENTER);
            }
            window.repaint();
            if (isClicking) {
                clickConditions();
            } else if (isPressing) {
                keyPressConditions();
            }
        });
    }

    public void keyPresser() {
        KPButton.addActionListener(e -> keyPressConditions());
        intervalKPTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        keyPressTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                KPButton.setEnabled(true);
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    KPButton.setEnabled(false);
                }else if (keyPressTextField.getText().length() > 0) {
                    keyPressTextField.setText("");
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    public void keyPressConditions() {
        isPressing = !isPressing;
        keyPress.setIsPressing(isPressing, getInterval(intervalKPTextField));
        keyPress.setKeyCode(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(keyPressTextField.getText().charAt(0)));
        if (isPressing) {
            KPButton.setText("Stop Key Press");
            intervalKPTextField.setFocusable(false);
            keyPressTextField.setFocusable(false);
            keyPress.start();
        } else {
            keyPress.worker.cancel(true);
            KPButton.setText("Start Key Press");
            intervalKPTextField.setFocusable(true);
            keyPressTextField.setFocusable(true);
        }
    }

    public void autoClicker() throws NativeHookException {
        intervalACTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    intervalACTextField.setFocusable(false);
                    clickConditions();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        ACButton.addActionListener(e -> clickConditions());
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_F1) {
                    isClicking = false;
                    clickConditions();
                } else if (e.getKeyCode() == NativeKeyEvent.VC_F2) {
                    isClicking = true;
                    clickConditions();
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            }
        });
    }
    public void clickConditions() {

        isClicking = !isClicking;
        autoClick.setIsClicking(isClicking, getInterval(intervalACTextField));

        if (isClicking) {
            ACButton.setText("Stop Auto Clicker (F2)");
            intervalACTextField.setFocusable(false);
            autoClick.start();
        } else {
            autoClick.worker.cancel(true);
            ACButton.setText("Start Auto Clicker (F1)");
            intervalACTextField.setFocusable(true);
        }

    }

    public int getInterval(JTextField field) {
        int integer;
        try {
            integer = Integer.parseInt(field.getText());
        } catch (NumberFormatException ex) {
            integer = 300000;
        }
        if (integer > 300000) {
            integer = 300000;
        }
        else if (integer < 250){
            integer = 250;
        }
        field.setText(String.valueOf(integer));
        return integer;
    }

}
