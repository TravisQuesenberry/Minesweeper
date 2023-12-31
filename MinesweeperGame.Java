package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MinesweeperGame {
    private JFrame frame;
    private JPanel gamePanel;
    private JButton[][] buttons;
    private int[][] mines;
    private int rows = 10;
    private int cols = 10;
    private int totalMines = 20;

    public MinesweeperGame() {
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        gamePanel = new JPanel(new GridLayout(rows, cols));
        buttons = new JButton[rows][cols];
        mines = new int[rows][cols];

        initializeGame();
        frame.add(gamePanel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> restartGame());

        JPanel restartPanel = new JPanel();
        restartPanel.add(restartButton);

        frame.add(restartPanel, BorderLayout.NORTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void initializeGame() {
        Random rand = new Random();
        for (int i = 0; i < totalMines; i++) {
            int row, col;
            do {
                row = rand.nextInt(rows);
                col = rand.nextInt(cols);
            } while (mines[row][col] == -1);
            mines[row][col] = -1;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mines[i][j] != -1) {
                    int count = countAdjacentMines(i, j);
                    mines[i][j] = count;
                }
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(50, 50));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 14));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                gamePanel.add(buttons[i][j]);
            }
        }
    }

    private void restartGame() {
        gamePanel.removeAll();
        buttons = new JButton[rows][cols];
        mines = new int[rows][cols];

        initializeGame();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gamePanel.add(buttons[i][j]);
            }
        }

        gamePanel.revalidate();
        frame.pack();
    }

    private int countAdjacentMines(int row, int col) {
      int count = 0;
      for (int r = row - 1; r <= row + 1; r++) {
          for (int c = col - 1; c <= col + 1; c++) {
              if (r >= 0 && r < rows && c >= 0 && c < cols) {
                  if (mines[r][c] == -1) {
                      count++;
                  }
              }
          }
      }
      return count;
  }



        private class ButtonClickListener implements ActionListener {
          private int row;
          private int col;

          public ButtonClickListener(int row, int col) {
              this.row = row;
              this.col = col;
          }

          @Override
          public void actionPerformed(ActionEvent e) {
            if (mines[row][col] == -1) {
                buttons[row][col].setText("X");
                gameOver();
            } else if (mines[row][col] >= 0) {
                buttons[row][col].setText(String.valueOf(mines[row][col]));
                buttons[row][col].setEnabled(false);
            } else {
                revealEmptyTiles(row, col);
            }
        }

                
          private void gameOver() {
            for (int i = 0; i < rows; i++) {
              for (int j = 0; j < cols; j++) {
                  buttons[i][j].setEnabled(false);
                  if (mines[i][j] == -1) {
                      buttons[i][j].setText("X");
                  }
              }
          }
      }
    }

      private void revealEmptyTiles(int row, int col) {
          if (row < 0 || row >= rows || col < 0 || col >= cols || !buttons[row][col].isEnabled()) {
            System.out.print("Bruh");
              return;
          }
          buttons[row][col].setEnabled(false);
          for (int r = row - 1; r <= row + 1; r++) {
              for (int c = col - 1; c <= col + 1; c++) {
                  if (r >= 0 && r < rows && c >= 0 && c < cols) {
                      revealEmptyTiles(r, c);
                  }
              }
          }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MinesweeperGame());
    }
}
