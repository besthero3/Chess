public class King extends Piece {

    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}
