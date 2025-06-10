public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}
