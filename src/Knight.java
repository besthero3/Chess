import com.sun.tools.javac.Main;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {
        int[] pieceCoordinates = new int[2];

        if (captures) {
            //no piece there
            if (MainCLI.board[row][col].p == null) {
                return null;
                //need to wrap this because i want to avoid a null pointer error
            }
            else {
                //can't take a piece that is the same color as your piece
                if (MainCLI.board[row][col].p.color == color) {
                    return null;
                }
            }
        }

        //up
        if (row >= 2) {
            if (col > 0) {
                if (MainCLI.board[row - 2][col - 1].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color == color) {
                    pieceCoordinates[0] = row - 2;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }
            }
            if(col < 7) {
                if (MainCLI.board[row - 2][col + 1].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color == color) {
                    pieceCoordinates[0] = row - 2;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }
        }

        //down
        if (row <= 5) {
            if (col > 0) {

                if (MainCLI.board[row + 2][col - 1].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color == color) {
                    pieceCoordinates[0] = row + 2;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }

            }
            if(col < 7) {
                if (MainCLI.board[row + 2][col + 1].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color == color) {
                    pieceCoordinates[0] = row + 2;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }
        }

        //left
        if (col >= 2) {
            if (row > 0) {
                if (MainCLI.board[row - 1][col - 2].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color == color) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col - 2;
                    return pieceCoordinates;
                }
            }
            if(row < 7) {
                if (MainCLI.board[row + 1][col - 2].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color == color) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col - 2;
                    return pieceCoordinates;
                }
            }
        }

        //right
        if (col <= 5) {
            if (row > 0) {
                if (MainCLI.board[row - 1][col + 2].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color == color) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col + 2;
                    return pieceCoordinates;
                }
            }
            if(row < 7) {
                if (MainCLI.board[row + 1][col + 2].p.type == PieceType.KNIGHT && MainCLI.board[row][col].p.color == color) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col + 2;
                    return pieceCoordinates;
                }
            }
        }

        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}
