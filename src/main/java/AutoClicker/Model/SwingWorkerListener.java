package AutoClicker.Model;

import javax.swing.*;

public interface SwingWorkerListener {
    void onSwingWorkerDone(SwingWorker<?, ?> worker);
    void onSwingWorkerStart(SwingWorker<?, ?> worker);
}
