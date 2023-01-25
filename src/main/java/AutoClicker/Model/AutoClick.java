package AutoClicker.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class AutoClick implements AutoFunction {
    private static final ArrayList<SwingWorkerListener> listeners = new ArrayList<>();
    private SwingWorker<Void, Void> worker;
    private int interval;
    private int maxClicks;

    private void createNewSwingWorker(){
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    for (SwingWorkerListener listener : listeners) {
                        listener.onSwingWorkerStart(this);
                    }
                    function();
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

    @Override
    public SwingWorker<Void, Void> getWorker() {
        return worker;
    }

    @Override
    public void start(int interval, int maxRuns) {
        this.interval = interval;
        maxClicks = maxRuns;
        createNewSwingWorker();
        worker.execute();
    }

    private void function() throws AWTException, InterruptedException {
        Robot bot = new Robot();
        int numberOfClicks = 0;
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        while(numberOfClicks < maxClicks) {
        lock.lock();
        try {
            condition.await(interval, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
            numberOfClicks++;
            new Timer(String.valueOf(interval));// After interval press and release on the mouse left click.
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
            worker.cancel(true);
    }

    public static void addSwingWorkerListener(SwingWorkerListener listener) {
        listeners.add(listener);
    }
}
