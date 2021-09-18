package AutoClicker.Model;

import java.awt.*;
import java.awt.event.InputEvent;

public class Model {

public void buttonClickEvent(Boolean isClicking) {
            while (isClicking){
                try {
                    click();
                } catch (AWTException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
}

    public void click() throws AWTException, InterruptedException {
        Robot bot = new Robot();
        Thread.sleep(1000);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(400);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }
}
