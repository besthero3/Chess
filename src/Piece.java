abstract public class Piece {

    PieceColor color;
    PieceType type;
    boolean firstMove;
    int[][] coordinate;

    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
        this.firstMove = true;
    }

    abstract int[] isValidMove(int row, int col, PieceColor color, boolean captures);
    //abstract int[] isValidCapture(int row, int col, PieceColor color);

    PieceColor determineColor(int color) {
        if (color == 0) {
            return PieceColor.WHITE;
        }
        else {
            return PieceColor.BLACK;
        }
    }
}
