package components.ui;

import model.Card;

import javax.swing.*;
import java.awt.*;

public class CardUI extends JLabel {

    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 115;

    public CardUI(Card card) {
        ImageIcon icon = new ImageIcon("images/cards/" + card.toImageName());

        Image scaledImage = icon.getImage().getScaledInstance(
                CARD_WIDTH,
                CARD_HEIGHT,
                Image.SCALE_SMOOTH
        );

        setIcon(new ImageIcon(scaledImage));
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setSize(CARD_WIDTH, CARD_HEIGHT);
    }
}