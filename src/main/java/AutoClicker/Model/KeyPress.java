package AutoClicker.Model;

import java.awt.*;

public class KeyPress  implements  Runnable{

    private boolean isPressing = false;
    private int interval;
    private int keyCode;

    public void setIsPressing(boolean isPressing, int interval) {
        this.isPressing = isPressing;
        this.interval =interval;
    }

    public void setKeyCode(int keyCode){
        this.keyCode = keyCode;
    }

    @Override
    public void run() {
        while (isPressing) {
            try {
                press();
            } catch (InterruptedException | AWTException e) {
                e.printStackTrace();
            }
        }
    }
    public void press() throws InterruptedException, AWTException {
        Robot bot = new Robot();
        while (isPressing) {
            Thread.sleep(interval);
            bot.keyPress(keyCode);
        }
    }
}
