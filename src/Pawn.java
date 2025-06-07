public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
    }

    @Override
    boolean isValid(int row, int col) {
        return false;
    }
}
