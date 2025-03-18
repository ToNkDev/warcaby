import javax.swing.*;
import java.awt.*;

public class board_F extends JFrame {
    public board_F()
    {
        super("Board");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500,500));
        add(new slots_p());
        setVisible(true);
    }
}
