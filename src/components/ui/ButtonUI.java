package components.ui;

import javax.swing.*;
import java.awt.*;

public class ButtonUI extends JButton {

    public ButtonUI(String text) {
        super(text);

        setFont(new Font("SansSerif", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setBackground(new Color(52, 152, 219));

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new Insets(10, 20, 10, 20));
    }

}
