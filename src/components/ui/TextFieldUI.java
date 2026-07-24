package components.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class TextFieldUI extends JTextField {

    public TextFieldUI(int columns) {
        super(columns);

        setFont(new Font("SansSerif", Font.PLAIN, 15));
        setPreferredSize(new Dimension(220, 40));

        setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));

        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setCaretColor(Color.BLACK);
    }
}