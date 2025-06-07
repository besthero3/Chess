public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    boolean isValid(int row, int col) {
        return false;
    }
}
