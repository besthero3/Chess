abstract public class Piece {

    PieceColor color;
    PieceType type;
    int[][] coordinate;

    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    abstract boolean isValid(int row, int col);

    PieceColor determineColor(int color) {
        if (color == 0) {
            return PieceColor.WHITE;
        }
        else {
            return PieceColor.BLACK;
        }
    }




}
