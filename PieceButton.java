package checkers;


import javax.swing.JButton;

public class PieceButton extends JButton {
    public int row;
    public int column;

    public PieceButton(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
