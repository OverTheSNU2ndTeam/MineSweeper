package kr.ac.snu.overthesnu;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame {
    static final int TILE_SIZE = 30;

    private final BaseMineSweeper baseMineSweeper;
    private final JButton[][] buttons;
    private final int width;
    private final int height;

    private boolean firstClick = true;
    private boolean gameEnded = false;

    public GameWindow() {
        baseMineSweeper = new BaseMineSweeper();
        baseMineSweeper.init();
        width = baseMineSweeper.getWidth();
        height = baseMineSweeper.getHeight();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        GridLayout gridLayout = new GridLayout(height, width);
        setLayout(gridLayout);

        buttons = new JButton[width][height];
        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; i++) {
                int x = i, y = j;
                JButton button = new JButton();
                button.setFocusable(false);
                button.setBorder(BorderFactory.createBevelBorder(0));

                // on left click
                button.addActionListener(e -> {
                    if (firstClick) {
                        baseMineSweeper.startGame(x, y);
                        firstClick = false;
                    } else {
                        baseMineSweeper.openTile(x, y);
                        if (baseMineSweeper.isEnded()) gameEnded = true;
                    }
                    updateUI();
                });

                // on right click
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (firstClick) return;
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            baseMineSweeper.toggleFlag(x, y);
                        }
                        updateUI();
                    }
                });

                add(button);
                buttons[i][j] = button;
            }
        }

        setTitle("Minesweeper");
        setSize(width * TILE_SIZE, height * TILE_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void updateUI() {
        setTitle("Minesweeper (remaining: " + baseMineSweeper.getNumOfMinesNotFound() + ")");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; ++j) {
                BaseTile baseTile = baseMineSweeper.getBaseTile(i, j);
                switch (baseTile.getState()) {
                    case BaseMineSweeperConstants.UNIDENTIFIED_TILE:
                        buttons[i][j].setText(baseTile.getFlag() ? "\uD83D\uDEA9" : "");
                        break;
                    case BaseMineSweeperConstants.EXPLODED_TILE:
                        buttons[i][j].setText("\uD83D\uDCA5");
                        break;
                    case BaseMineSweeperConstants.NUMBERED_TILE:
                        int number = ((BaseNumberTile)baseTile).getNumber();
                        buttons[i][j].setText(number == 0 ? "" : Integer.toString(number));
                        buttons[i][j].setBorderPainted(false);
                        break;
                }
                if (gameEnded) {
                    buttons[i][j].setEnabled(false);
                }
            }
        }
    }
}
