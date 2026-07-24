package components;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        this.setResizable(false);
        this.setSize(500, 500);
        this.setLayout(null);
        this.setVisible(true);
    }


}
