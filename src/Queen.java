public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}
