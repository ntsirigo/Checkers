package checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardGUI extends JFrame {
    private Board board;
    private PieceButton[][] buttons;
    private int playerTurn = 1;
    private PieceButton selectedButton;

    public BoardGUI() {
        board = new Board();
        buttons = new PieceButton[8][8];
        setTitle("Checkers");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        initializeBoard();
        setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                PieceButton button = new PieceButton(i, j);
                button.setText(board.board[i][j].placeholder);
                button.setFont(new Font("Arial", Font.PLAIN, 40));
                button.setFocusPainted(false);
                button.setBackground((i + j) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                button.setForeground((i + j) % 2 == 0 ? Color.DARK_GRAY : Color.LIGHT_GRAY);
                button.addActionListener(new ButtonClickListener());
                buttons[i][j] = button;
                add(button);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            PieceButton button = (PieceButton) e.getSource();
            if (selectedButton == null) {
                if (board.board[button.row][button.column].value == playerTurn) {
                    selectedButton = button;
                    button.setBackground(Color.YELLOW);
                }
            } else {
                String move = Board.ROWS[selectedButton.row] + "-" + (selectedButton.column + 1) + " > "
                        + Board.ROWS[button.row] + "-" + (button.column + 1);
                Move m = new Move(move, board);
                if (m.isValid(playerTurn)) {
                    if (m.move()) {
                        if (board.hasLost(playerTurn == 1 ? 2 : 1)) {
                            JOptionPane.showMessageDialog(null, "Player " + playerTurn + " wins!");
                            System.exit(0);
                        }
                        playerTurn = playerTurn == 1 ? 2 : 1;
                        updateBoard();
                    }
                }
                selectedButton.setBackground((selectedButton.row + selectedButton.column) % 2 == 0 ? Color.LIGHT_GRAY : Color.DARK_GRAY);
                selectedButton = null;
            }
        }
    }

    private void updateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setText(board.board[i][j].placeholder);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoardGUI::new);
    }
}
