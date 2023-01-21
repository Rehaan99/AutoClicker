package AutoClicker.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class KeyPress {
    private static ArrayList<SwingWorkerListener> listeners = new ArrayList<>();
    public SwingWorker<Void, Void> worker;
    private boolean isPressing = false;
    private int interval;
    private int keyCode;
    private int baseInterval;
    private int maxPresses;

    public static void addSwingWorkerListener(SwingWorkerListener listener) {
        listeners.add(listener);
    }

    public void setIsPressing(boolean isPressing, int interval, int maxPresses) {
        this.isPressing = isPressing;
        this.interval = interval;
        this.baseInterval = interval;
        this.maxPresses = maxPresses;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    private void createNewSwingWorker() {
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    press();
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

    public void start() {
        createNewSwingWorker();
        worker.execute();
    }

    public void press() throws InterruptedException, AWTException {
        Robot bot = new Robot();
        int numberOfPresses = 0;
        while (isPressing && numberOfPresses < maxPresses) {
            numberOfPresses++;
            Thread.sleep(interval);
            bot.keyPress(keyCode);
            Thread.sleep(200);
            bot.keyRelease(keyCode);
            if (interval == baseInterval) {
                interval -= 200;
            }
        }
        worker.cancel(true);
    }
}
