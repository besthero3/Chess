public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    boolean isValid(int row, int col) {
        return false;
    }
}
