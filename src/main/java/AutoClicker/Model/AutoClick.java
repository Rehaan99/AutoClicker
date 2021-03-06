package AutoClicker.Model;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoClick implements Runnable{

    private boolean isClicking = false;
    private int interval;

    public void setIsClicking(boolean isClicking, int interval) {
        this.isClicking = isClicking;
        this.interval =interval;
    }

    @Override
    public void run() {
        while (isClicking) {
            try {
            click();
                } catch (AWTException | InterruptedException ex) {
            ex.printStackTrace();
            }
        }
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
