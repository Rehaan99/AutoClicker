package AutoClicker.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClick {

    private boolean isClicking = false;
    private int interval;
    public SwingWorker<Void,Void> worker;

    public void setIsClicking(boolean isClicking, int interval) {
        this.isClicking = isClicking;
        this.interval =interval;
    }

    public void start() {
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                while (isClicking) {
                    try {
                        click();
                    } catch (AWTException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    private void click() throws AWTException, InterruptedException {
        Robot bot = new Robot();
        while (isClicking) {
            Thread.sleep(interval);// After interval press and release on the mouse left click.
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }

}
