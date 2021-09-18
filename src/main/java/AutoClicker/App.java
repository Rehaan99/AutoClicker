package AutoClicker;

import AutoClicker.Controller.Controller;
import AutoClicker.Model.Model;
import AutoClicker.View.GUI;

public class App {

    public static void main(String[] args) {
        GUI myGUI = new GUI();
        Model myModel = new Model();
        Controller myController = new Controller(myModel, myGUI);
    }

}