package AutoClicker.Model;

import javax.swing.*;
import java.awt.*;

public class KeyPress {

    private boolean isPressing = false;
    private int interval;
    private int keyCode;
    public SwingWorker<Void,Void> worker;

    public void setIsPressing(boolean isPressing, int interval) {
        this.isPressing = isPressing;
        this.interval =interval;
    }

    public void setKeyCode(int keyCode){
        this.keyCode = keyCode;
    }

    public void start() {
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                while (isPressing) {
                    try {
                        press();
                    } catch (InterruptedException | AWTException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    public void press() throws InterruptedException, AWTException {
        Robot bot = new Robot();
        while (isPressing) {
            Thread.sleep(interval);
            bot.keyPress(keyCode);
            Thread.sleep(200);
            bot.keyRelease(keyCode);
        }
    }
}
