public class NoPiece extends Piece {

    public NoPiece() {
        super(PieceColor.EMPTY, PieceType.NONE);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        return null;
    }
}
