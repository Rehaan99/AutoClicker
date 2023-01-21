package AutoClicker.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;

public class AutoClick {
    private static ArrayList<SwingWorkerListener> listeners = new ArrayList<>();
    public SwingWorker<Void, Void> worker;
    private boolean isClicking = false;
    private int interval;
    private int maxClicks;

    private void createNewSwingWorker(){
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    click();
                } catch (AWTException | InterruptedException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
            @Override
            protected void done() {
                for (SwingWorkerListener listener : listeners) {
                    listener.onSwingWorkerDone(this);
                }
            }
        };
    }

    public static void addSwingWorkerListener(SwingWorkerListener listener) {
        listeners.add(listener);
    }

    public void setIsClicking(boolean isClicking, int interval, int maxClicks) {
        this.isClicking = isClicking;
        this.interval = interval;
        this.maxClicks = maxClicks;
    }

    public void start() {
        createNewSwingWorker();
        worker.execute();
    }

    private void click() throws AWTException, InterruptedException {
        Robot bot = new Robot();
        int numberOfClicks = 0;
        while (isClicking && numberOfClicks < maxClicks) {
            numberOfClicks++;
            Thread.sleep(interval);// After interval press and release on the mouse left click.
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
        worker.cancel(true);
    }
}
