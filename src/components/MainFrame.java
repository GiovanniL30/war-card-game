package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        BorderLayout borderLayout = new BorderLayout();

        JPanel mainLayout = new JPanel();
        mainLayout.setBorder(new EmptyBorder(25, 50, 25, 50));
        setContentPane(mainLayout);

        getContentPane().setLayout(borderLayout);
        setVisible(true);
        setResizable(false);
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
