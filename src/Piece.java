/**
 * Represents a Chess Piece. Abstract class because every piece object should inherit behavior but a piece object
 * should not be able to be created.
*/
abstract public class Piece {

    PieceColor color;
    PieceType type;
    boolean firstMove; //useful for pawns
    int[][] coordinate;

    /**
     * Piece Constructor
     * */
    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
        this.firstMove = true;
    }

    /**
     *Abstract method to check if a move is valid, every type of piece should implement this
     * @param row - row of piece
     * @param col - col of piece
     * @param color - piece color
     * @param captures - captures apiece
     * @return an array that indicates a valid square or null
     * */
    abstract int[] isValidMove(int row, int col, PieceColor color, boolean captures);

}
