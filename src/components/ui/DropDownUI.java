package components.ui;

import javax.swing.*;
import java.awt.*;

public class DropDownUI extends JPanel {

    private final JComboBox<String> comboBox;

    public DropDownUI(String title, String[] options) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        comboBox = new JComboBox<>(options);
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBox.setMaximumSize(new Dimension(400, 30));
        comboBox.setSelectedIndex(0);

        add(titleLabel);
        add(Box.createVerticalStrut(5));
        add(comboBox);
    }

    public String getSelectedItem() {
        return (String) comboBox.getSelectedItem();
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }
}
