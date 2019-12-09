import javax.swing.*;
import java.awt.*;

public class SnakeFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public SnakeFrame() {
        SnakePanel gamePanel = new SnakePanel();
        Container cont = getContentPane();
        cont.add(gamePanel);
        setTitle("Neilo snake game");
        setBounds(0, 0, 800, 650);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}