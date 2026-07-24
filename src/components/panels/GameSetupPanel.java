package components.panels;

import components.ui.ButtonUI;
import components.ui.DropDownUI;
import game.GameFileManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.nio.file.Path;
import java.util.List;

public class GameSetupPanel extends JPanel {

    private final GameFileManager fileManager = new GameFileManager();

    private final DropDownUI playerCountDropDown =
            new DropDownUI("Player Count",
                    new String[]{"2", "3", "4", "5", "6", "7", "8"});

    private final DropDownUI shuffleCountDropDown =
            new DropDownUI("Shuffle Count",
                    new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});

    private final DropDownUI deckFileDropDown;

    private final ButtonUI startGameButton =
            new ButtonUI("Start Game");

    public GameSetupPanel() {

        List<Path> files = fileManager.getFilePaths();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Welcome to War Card Game");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 24f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titleLabel);
        add(Box.createVerticalStrut(30));

        // Player count
        playerCountDropDown.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Shuffle count
        shuffleCountDropDown.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Deck file
        String[] pathNames = fileManager.getFilePaths()
                .stream()
                .map(path -> path.getFileName().toString())
                .toArray(String[]::new);
        deckFileDropDown = new DropDownUI("Game Deck", pathNames);
        deckFileDropDown.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Start button
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(playerCountDropDown);
        add(Box.createVerticalStrut(20));

        add(shuffleCountDropDown);
        add(Box.createVerticalStrut(20));

        add(deckFileDropDown);
        add(Box.createVerticalStrut(20));

        add(startGameButton);
    }

    public int getPlayerCount() {
        return Integer.parseInt(playerCountDropDown.getSelectedItem());
    }

    public int getShuffleCount() {
        return Integer.parseInt(shuffleCountDropDown.getSelectedItem());
    }

    public ButtonUI getStartGameButton() {
        return startGameButton;
    }
}