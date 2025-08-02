public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color, PieceType.BISHOP);
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
        else {
            if (MainCLI.board[row][col].p != null && MainCLI.board[row][col].p.type != PieceType.NONE) {
                return null;
                //need to wrap this because i want to avoid a null pointer error
            }
        }

        //row - 1, col -1. up left
        if (row - 1 >= 0 && col - 1 >= 0) {
            int j = col - 1;

            for(int i = row - 1; i >= 0; i--) {
                //error checking

                if (j < 0) {
                    break;
                }

                //if not an empty square
                if (MainCLI.board[i][j].p != null && MainCLI.board[i][j].p.type != PieceType.NONE) {

                    //the piece type matches and the color matches the piece we are moving
                    if (MainCLI.board[i][j].p.type == PieceType.BISHOP && MainCLI.board[i][j].p.color == color) {
                        pieceCoordinates[0] = i;
                        pieceCoordinates[1] = j;
                        return pieceCoordinates;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j--;
            }

        }

        //row - 1 col + 1. up right
        if (row - 1 >= 0 && col + 1 < 8) {
            int j = col + 1;

            for(int i = row - 1; i >= 0; i--) {

                if (j > 7) {
                    break;
                }

                //if not an empty square
                if (MainCLI.board[i][j].p != null && MainCLI.board[i][j].p.type != PieceType.NONE) {

                    //the piece type matches and the color matches the piece we are moving
                    if (MainCLI.board[i][j].p.type == PieceType.BISHOP && MainCLI.board[i][j].p.color == color) {
                        pieceCoordinates[0] = i;
                        pieceCoordinates[1] = j;
                        return pieceCoordinates;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j++;
            }
        }

        //row + 1, col - 1. down left
        if (row + 1 < 8 && col - 1 >= 0) {
            int j = col - 1;

            for(int i = row + 1; i < 8; i++) {

                if (j < 0) {
                    break;
                }

                //if not an empty square
                if (MainCLI.board[i][j].p != null && MainCLI.board[i][j].p.type != PieceType.NONE) {

                    //the piece type matches and the color matches the piece we are moving
                    if (MainCLI.board[i][j].p.type == PieceType.BISHOP && MainCLI.board[i][j].p.color == color) {
                        pieceCoordinates[0] = i;
                        pieceCoordinates[1] = j;
                        return pieceCoordinates;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j--;
            }
        }

        //row + 1, col + 1. down right
        if (row + 1 < 8 && col + 1 < 8) {
            int j = col + 1;

            for(int i = row + 1; i < 8; i++) {

                if (j > 7) {
                    break;
                }

                //if not an empty square
                if (MainCLI.board[i][j].p != null && MainCLI.board[i][j].p.type != PieceType.NONE) {

                    //the piece type matches and the color matches the piece we are moving
                    if (MainCLI.board[i][j].p.type == PieceType.BISHOP && MainCLI.board[i][j].p.color == color) {
                        pieceCoordinates[0] = i;
                        pieceCoordinates[1] = j;
                        return pieceCoordinates;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j++;
            }
        }

        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }
}
