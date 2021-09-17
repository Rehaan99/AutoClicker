package AutoClicker.UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class Engine {

    public static boolean isClicking;
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    public Engine() {

        JButton startAutoClicker = new JButton("Start Auto Clicker");
        JButton endAutoClicker = new JButton("Stop Auto Clicker");
        panel.setBorder(BorderFactory.createEmptyBorder(100,300,100,300));
        panel.setLayout(new GridLayout(0,1));
        panel.add(startAutoClicker);
        panel.add(endAutoClicker);
        startAutoClicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isClicking = true;
                while (isClicking){
                    try {
                        click();
                    } catch (AWTException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        endAutoClicker.addActionListener(e -> isClicking = false);
        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Auto Clicker");
        frame.pack();
        frame.setVisible(true);
    }
    public void click() throws AWTException, InterruptedException {
        Robot bot = new Robot();
            Thread.sleep(1000);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep(400);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

    }
}
