package AutoClicker.Model;

import java.awt.*;
import java.awt.event.InputEvent;

public class Model implements Runnable{

    private boolean isClicking = false;
    private int interval;

    public void setIsClicking(boolean isClicking, int interval) {
        this.isClicking = isClicking;
        this.interval =interval;
    }

    @Override
    public void run() {
        try {
            buttonClickEvent();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void buttonClickEvent() throws InterruptedException {;
        while (isClicking) {
            try {
                click();
            } catch (AWTException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Stopping");
    }

    public void click() throws AWTException, InterruptedException {
        Robot bot = new Robot();
        Thread.sleep(interval-100);
        System.out.println("Click");
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }

}
