package AutoClicker;

import AutoClicker.Controller.Controller;
import AutoClicker.Model.AutoClick;
import AutoClicker.Model.KeyPress;
import AutoClicker.View.GUI;
import org.jnativehook.NativeHookException;

public class App {

    public static void main(String[] args) throws NativeHookException {
        GUI myGUI = new GUI();
        AutoClick myAutoClick = new AutoClick();
        KeyPress keyPress = new KeyPress();
        Controller myController = new Controller(myAutoClick, myGUI, keyPress);
    }

}