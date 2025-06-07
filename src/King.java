public class King extends Piece {

    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    boolean isValid(int row, int col) {
        return false;
    }
}
