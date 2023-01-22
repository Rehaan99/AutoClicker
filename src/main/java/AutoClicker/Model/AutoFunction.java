package AutoClicker.Model;

import javax.swing.*;

public interface AutoFunction {
    SwingWorker<Void, Void> getWorker();
    void start(int interval, int maxRuns);

}
