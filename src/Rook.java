public class Rook extends Piece {


    public Rook(PieceColor pieceColor) {
        super(pieceColor, PieceType.ROOK);
    }

    @Override
    boolean isValid(int row, int col) {
        return false;
    }


}
