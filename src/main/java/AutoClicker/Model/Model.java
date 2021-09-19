package AutoClicker.Model;

import java.awt.*;
import java.awt.event.InputEvent;

public class Model implements Runnable{

    private boolean isClicking = false;

    public void setIsClicking(boolean isClicking) {
        this.isClicking = isClicking;
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
        Thread.sleep(1000);
        System.out.println("Click");
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(400);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }

}
