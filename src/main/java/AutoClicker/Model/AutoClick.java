package AutoClicker.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;

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
                timer.stop();
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
    private int numberOfClicks = 0;
    Timer timer;
    private void function() throws AWTException, InterruptedException {
        Robot bot = new Robot();
        timer = new Timer(interval, e -> {
            numberOfClicks++;
            if(numberOfClicks < maxClicks) {
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }else {
                worker.cancel(true);
            }
        });
        timer.start();
    }

    public static void addSwingWorkerListener(SwingWorkerListener listener) {
        listeners.add(listener);
    }
}
