package AutoClicker.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KeyPress implements AutoFunction {
    private static final ArrayList<SwingWorkerListener> listeners = new ArrayList<>();
    private SwingWorker<Void, Void> worker;
    private int interval;
    private int keyCode;
    private int baseInterval;
    private int maxPresses;

    public static void addSwingWorkerListener(SwingWorkerListener listener) {
        listeners.add(listener);
    }

    private void createNewSwingWorker() {
        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    for (SwingWorkerListener listener : listeners) {
                        listener.onSwingWorkerStart(this);
                    }
                    automation();
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

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public SwingWorker<Void, Void> getWorker() {
        return worker;
    }

    @Override
    public void start(int interval, int maxRuns) {
        this.interval = interval;
        this.baseInterval = interval;
        maxPresses = maxRuns;
        createNewSwingWorker();
        worker.execute();
    }

    private void automation() throws InterruptedException, AWTException {
        Robot bot = new Robot();
        int numberOfPresses = 0;
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        while (numberOfPresses < maxPresses) {
            numberOfPresses++;
            lock.lock();
            try {
                condition.await(interval, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            bot.keyPress(keyCode);
            try {
                condition.await(200, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            bot.keyRelease(keyCode);
            if (interval == baseInterval) {
                interval -= 200;
            }
        }
        worker.cancel(true);
    }
}
