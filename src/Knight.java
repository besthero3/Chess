public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}
