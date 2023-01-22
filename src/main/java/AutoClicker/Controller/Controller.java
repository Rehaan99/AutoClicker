package AutoClicker.Controller;

import AutoClicker.Model.AutoClick;
import AutoClicker.Model.AutoFunction;
import AutoClicker.Model.KeyPress;
import AutoClicker.Model.SwingWorkerListener;
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
    private final JButton ACButton;
    private final JTextField intervalACTextField;
    private final JButton KPButton;
    private final JTextField intervalKPTextField;
    private final JTextField keyPressTextField;
    private final JButton settingsButton;
    private final JButton runAllButton;
    private final JPanel functionsPanel;
    private final JPanel settingsPanel;
    private final JFrame window;
    private boolean isClicking = false;
    private boolean isPressing = false;
    ///private boolean pressAll = false;
    private boolean isVisible = true;
    private int maxFunctions = 7200; // temporary value, another object needs to be added to the GUI and then that value can be read and passed here (7200 is an hour of clicks at the fastest speed of 500ms)


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
        runAllButton = gui.getRunAllButton();
        createListeners();
    }

    private void settingsButtonPressed() {
        isVisible = !isVisible;
        if (isVisible) {
            window.remove(settingsPanel);
            window.add(functionsPanel, BorderLayout.CENTER);
        } else {
            window.remove(functionsPanel);
            window.add(settingsPanel, BorderLayout.CENTER);
        }
        window.repaint();
        if (isClicking) {
            doFunction(autoClick, getInterval(intervalACTextField), maxFunctions);
        }
        if (isPressing) {
            doFunction(keyPress, getInterval(intervalKPTextField), maxFunctions);
        }
    }

    private void createListeners() throws NativeHookException {
        settingsButton.addActionListener(e -> settingsButtonPressed());

        KPButton.addActionListener(e -> {
            keyPress.setKeyCode(java.awt.event.KeyEvent.getExtendedKeyCodeForChar(keyPressTextField.getText().charAt(0)));
            doFunction(keyPress, getInterval(intervalKPTextField), maxFunctions);
        });

        intervalKPTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        keyPressTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                KPButton.setEnabled(true);
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    KPButton.setEnabled(false);
                } else if (keyPressTextField.getText().length() > 0) {
                    keyPressTextField.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
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
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        ACButton.addActionListener(e -> doFunction(autoClick, getInterval(intervalACTextField), maxFunctions));
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                if (e.getKeyCode() == NativeKeyEvent.VC_F1 && !isClicking) {
                    doFunction(autoClick, getInterval(intervalACTextField), maxFunctions);
                } else if (e.getKeyCode() == NativeKeyEvent.VC_F2 && isClicking) {
                    doFunction(autoClick, getInterval(intervalACTextField), maxFunctions);
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            }
        });
        runAllButton.addActionListener(e -> startAll());

        AutoClick.addSwingWorkerListener(new SwingWorkerListener() {
            @Override
            public void onSwingWorkerDone(SwingWorker<?, ?> worker) {
                ACButton.setText("Start Auto Clicker (F1)");
                isClicking = false;
                intervalACTextField.setFocusable(true);
            }

            @Override
            public void onSwingWorkerStart(SwingWorker<?, ?> worker) {
                ACButton.setText("Stop Auto Clicker (F2)");
                isClicking = true;
                intervalACTextField.setFocusable(false);
            }
        });

        KeyPress.addSwingWorkerListener(new SwingWorkerListener() {
            @Override
            public void onSwingWorkerDone(SwingWorker<?, ?> worker) {
                KPButton.setText("Start Key Press");
                isPressing = false;
                setFocusable();
            }

            @Override
            public void onSwingWorkerStart(SwingWorker<?, ?> worker) {
                KPButton.setText("Stop Key Press");
                isPressing = true;
                setFocusable();
            }
        });
    }

    private void setFocusable() {
        intervalKPTextField.setFocusable(!isPressing);
        keyPressTextField.setFocusable(!isPressing);
    }

    private void doFunction(AutoFunction function, int interval, int maxFunctions) {
        if (function.getWorker() == null || function.getWorker().isDone()) {
            function.start(interval, maxFunctions);
        } else {
            function.getWorker().cancel(true);
        }
    }

    private int getInterval(JTextField field) {
        int interval;
        try {
            interval = Integer.parseInt(field.getText());
        } catch (NumberFormatException ex) {
            interval = 300000;
        }
        if (interval > 300000) {
            interval = 300000;
        } else if (interval < 250) {
            interval = 250;
        }
        field.setText(String.valueOf(interval));
        return interval;
    }

    private void startAll() {
//        pressAll = !pressAll;
//        if (pressAll) {
//            if (!isPressing & keyPressTextField.getText().length() > 0) {
//                keyPressConditions();
//            }
//            if (!isClicking) {
//                clickConditions();
//            }
//            runAllButton.setText("Stop All Functions");
//        } else {
//            if (isPressing) {
//                keyPressConditions();
//            }
//            if (isClicking) {
//                clickConditions();
//            }
//            runAllButton.setText("Start All Functions");
//        }
    }

}
