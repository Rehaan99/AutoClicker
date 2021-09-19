package AutoClicker;

import AutoClicker.Controller.Controller;
import AutoClicker.Model.Model;
import AutoClicker.View.GUI;
import org.jnativehook.NativeHookException;

public class App {

    public static void main(String[] args) throws NativeHookException {
        GUI myGUI = new GUI();
        Model myModel = new Model();
        Controller myController = new Controller(myModel, myGUI);
    }

}