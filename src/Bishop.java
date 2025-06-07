public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    boolean isValid(int row, int col) {
        return false;
    }
}
