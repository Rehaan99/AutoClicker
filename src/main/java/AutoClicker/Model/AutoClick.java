package AutoClicker.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClick {

    public SwingWorker<Void, Void> worker;
    private boolean isClicking = false;
    private int interval;

    public void setIsClicking(boolean isClicking, int interval) {
        this.isClicking = isClicking;
        this.interval = interval;
    }

    public void start(int maxPresses) {
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                    try {
                        click(maxPresses);
                    } catch (AWTException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                return null;
            }
        };
        worker.execute();
    }

    private void click(int maxPresses) throws AWTException, InterruptedException {
        Robot bot = new Robot();
        int numberOfClicks = 0;
        while (isClicking || numberOfClicks <= maxPresses) {
            numberOfClicks++;
            Thread.sleep(interval);// After interval press and release on the mouse left click.
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }

}
