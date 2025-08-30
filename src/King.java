public class King extends Piece {

    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    //TODO: have to fix all of the errors in this class - mainly with bishop and rook and queen things

    @Override
    //TODO: SHOULD CHECK IF NOT IN CHECK?
    int[] isValidMove(int row, int col, PieceColor color, boolean captures) {

        int[] pieceCoordinates = new int[2];
        //check one in every direction from row and col

        //only need to check this captures
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

        //off of the top edge of the board
        if(row > 0) {

            //top left
            if (col > 0) {
                if (MainCLI.board[row - 1][col - 1].p.type == PieceType.KING && MainCLI.board[row - 1][col - 1].p.color == color) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }
            }

            //top middle
            if (MainCLI.board[row - 1][col].p.type == PieceType.KING && MainCLI.board[row - 1][col].p.color == color) {
                pieceCoordinates[0] = row - 1;
                pieceCoordinates[1] = col;
                return pieceCoordinates;
            }

            if (col < 7) {
                //top right
                if (MainCLI.board[row - 1][col + 1].p.type == PieceType.KING && MainCLI.board[row - 1][col + 1].p.color == color) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }


        }

        //off the left edge of the board
        if(col > 0) {
            //middle left
            if (MainCLI.board[row][col - 1].p.type == PieceType.KING && MainCLI.board[row][col - 1].p.color == color) {
                pieceCoordinates[0] = row;
                pieceCoordinates[1] = col - 1;
                return pieceCoordinates;
            }
        }

        //off the right edge of the board
        if(col < 7) {
            //middle right
            if (MainCLI.board[row][col + 1].p.type == PieceType.KING && MainCLI.board[row][col + 1].p.color == color) {
                pieceCoordinates[0] = row;
                pieceCoordinates[1] = col + 1;
                return pieceCoordinates;
            }
        }

        //off the bottom edge of the board
        if(row < 7) {

            if (col > 0) {
                //bottom left
                if (MainCLI.board[row + 1][col - 1].p.type == PieceType.KING && MainCLI.board[row + 1][col - 1].p.color == color) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }
            }

            //bottom middle
            if (MainCLI.board[row + 1][col].p.type == PieceType.KING && MainCLI.board[row + 1][col].p.color == color) {
                pieceCoordinates[0] = row + 1;
                pieceCoordinates[1] = col;
                return pieceCoordinates;
            }

            if (col < 7) {
                //bottom right
                if (MainCLI.board[row + 1][col + 1].p.type == PieceType.KING && MainCLI.board[row + 1][col + 1].p.color == color) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }
        }

        return null;
    }

    int[] isValidCapture(int row, int col, PieceColor color) {
        return null;
    }


    //check function
    //need square to iterate from, int row, int col, (store where king is),
    //want color to be not equal to if we are checking for a check and then return true...
    boolean check(int row, int col, PieceColor color) {

        //<---------------------------------------HORIZONTAL/VERTICAL---------------------------------------->
        //back up the board...
        for(int i = row - 1; i >= 0; i--) {

            if (row - 1 < 0) {
                break;
            }
            //empty square
            if (MainCLI.board[i][col].p != null && MainCLI.board[i][col].p.type != PieceType.NONE) {
                if ((MainCLI.board[i][col].p.type == PieceType.ROOK || MainCLI.board[i][col].p.type == PieceType.QUEEN) && MainCLI.board[i][col].p.color != color) {
                    return true;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
                }
            }
        }

        //down the board
        for (int i = row + 1; i < 8; i++) {

            if (row + 1 > 7) {
                break;
            }

            //this checks to make sure that the piece type is not none or null (there is a piece there)
            //then we check to make sure it is of the opposite color
            if (MainCLI.board[i][col].p != null && MainCLI.board[i][col].p.type != PieceType.NONE) {
                if ((MainCLI.board[i][col].p.type == PieceType.ROOK || MainCLI.board[i][col].p.type == PieceType.QUEEN) && MainCLI.board[i][col].p.color != color) {
                    return true;
                }
                //TODO: check that we cant jump over pieces but have to check correctly...
                else {
                    break;
                }
            }
        }

        //to the left
        for (int i = col - 1; i >= 0; i--) {

            if (col - 1 < 0) {
                break;
            }

            if (MainCLI.board[row][i].p != null && MainCLI.board[row][i].p.type != PieceType.NONE) {
                if ((MainCLI.board[row][i].p.type == PieceType.ROOK || MainCLI.board[row][i].p.type == PieceType.QUEEN) && MainCLI.board[row][i].p.color != color) {
                    return true;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
                }
            }
        }

        // to the right
        for (int i = col + 1; i < 8; i++) {

            if (col + 1 > 7) {
                break;
            }

            if (MainCLI.board[row][i].p != null && MainCLI.board[row][i].p.type != PieceType.NONE) {
                if ((MainCLI.board[row][i].p.type == PieceType.ROOK || MainCLI.board[row][i].p.type == PieceType.QUEEN) && MainCLI.board[row][i].p.color != color) {
                    return true;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
                }
            }
        }
        //<---------------------------------------HORIZONTAL/VERTICAL---------------------------------------->

        //<---------------------------------KNIGHT-------------------------------------------->
        //up
        if (row >= 2) {
            if (col > 0) {
                if (MainCLI.board[row - 2][col - 1].p.type == PieceType.KNIGHT && MainCLI.board[row - 2][col - 1].p.color != color) {
                    return true;
                }
            }
            if(col < 7) {
                if (MainCLI.board[row - 2][col + 1].p.type == PieceType.KNIGHT && MainCLI.board[row - 2][col + 1].p.color != color) {
                    return true;
                }
            }
        }

        //down
        if (row <= 5) {
            if (col > 0) {

                if (MainCLI.board[row + 2][col - 1].p.type == PieceType.KNIGHT && MainCLI.board[row + 2][col - 1].p.color != color) {
                    return true;
                }

            }
            if(col < 7) {
                if (MainCLI.board[row + 2][col + 1].p.type == PieceType.KNIGHT && MainCLI.board[row + 2][col + 1].p.color != color) {
                    return true;
                }
            }
        }

        //left
        if (col >= 2) {
            if (row > 0) {
                if (MainCLI.board[row - 1][col - 2].p.type == PieceType.KNIGHT && MainCLI.board[row - 1][col - 2].p.color != color) {
                    return true;
                }
            }
            if(row < 7) {
                if (MainCLI.board[row + 1][col - 2].p.type == PieceType.KNIGHT && MainCLI.board[row + 1][col - 2].p.color != color) {
                    return true;
                }
            }
        }

        //right
        if (col <= 5) {
            if (row > 0) {
                if (MainCLI.board[row - 1][col + 2].p.type == PieceType.KNIGHT && MainCLI.board[row - 1][col + 2].p.color != color) {
                    return true;
                }
            }
            if(row < 7) {
                if (MainCLI.board[row + 1][col + 2].p.type == PieceType.KNIGHT && MainCLI.board[row + 1][col + 2].p.color != color) {
                    return true;
                }
            }
        }

        //<----------------------KNIGHT------------------------------------------------->>>>>>


        //<-------------------------DIAGONAL--------------------------------------------->
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
                    if ((MainCLI.board[i][j].p.type == PieceType.BISHOP || MainCLI.board[i][j].p.type == PieceType.QUEEN) && MainCLI.board[i][j].p.color != color) {
                        return true;
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
                    if ((MainCLI.board[i][j].p.type == PieceType.BISHOP || MainCLI.board[i][j].p.type == PieceType.QUEEN) && MainCLI.board[i][j].p.color != color) {
                        return true;
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
                    if ((MainCLI.board[i][j].p.type == PieceType.BISHOP || MainCLI.board[i][j].p.type == PieceType.QUEEN) && MainCLI.board[i][j].p.color != color) {
                        return true;
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
                    if ((MainCLI.board[i][j].p.type == PieceType.BISHOP || MainCLI.board[i][j].p.type == PieceType.QUEEN) && MainCLI.board[i][j].p.color != color) {
                        return true;
                    }
                    //we found a different type of piece and we cannot jump over it because this is a rook
                    else {
                        break;
                    }
                }
                j++;
            }
        }
        //<-------------------------DIAGONAL--------------------------------------------->

        //PAWN - opposites because check
        if (color == PieceColor.BLACK) {
            //value needs to be one more or one less to be able to capture as a pawn
            //plus because down the board
            if (row < 7 && col > 0) {
                if (MainCLI.board[row + 1][col - 1].p.type == PieceType.PAWN && MainCLI.board[row + 1][col - 1].p.color == PieceColor.WHITE) {
                    return true;
                }
            }

            if (row < 7 && col < 7) {
                if (MainCLI.board[row + 1][col + 1].p.type == PieceType.PAWN && MainCLI.board[row + 1][col + 1].p.color == PieceColor.WHITE) {
                    return true;
                }
            }
        }
        else if (color == PieceColor.WHITE) {
            if (row > 0 && col > 0) {
                if (MainCLI.board[row - 1][col - 1].p.type == PieceType.PAWN && MainCLI.board[row - 1][col - 1].p.color == PieceColor.BLACK) {
                    return true;
                }
            }

            if (row > 0 && col < 7) {
                if (MainCLI.board[row - 1][col + 1].p.type == PieceType.PAWN && MainCLI.board[row - 1][col + 1].p.color == PieceColor.BLACK) {
                    return true;
                }
            }
        }

        return false;
    }

    //going to return a row, col for where the piece is...
    //then see if square is in check...
    //TODO: NEED TO CHECK IF THE KING CAN TAKE A PIECE TOO
    int[] checkPiece(int row, int col, PieceColor color) {
        int[] pieceCoordinates = new int[2];


        //<---------------------------------------HORIZONTAL/VERTICAL---------------------------------------->
        //back up the board...
        for(int i = row - 1; i >= 0; i--) {

            if (row - 1 < 0) {
                break;
            }
            //empty square
            if (MainCLI.board[i][col].p != null && MainCLI.board[i][col].p.type != PieceType.NONE) {
                if ((MainCLI.board[i][col].p.type == PieceType.ROOK || MainCLI.board[i][col].p.type == PieceType.QUEEN) && MainCLI.board[i][col].p.color != color) {
                    pieceCoordinates[0] = i;
                    pieceCoordinates[1] = col;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
                }
            }
        }

        //down the board
        for (int i = row + 1; i < 8; i++) {

            if (row + 1 > 7) {
                break;
            }
            if (MainCLI.board[i][col].p != null && MainCLI.board[i][col].p.type != PieceType.NONE) {
                if ((MainCLI.board[i][col].p.type == PieceType.ROOK || MainCLI.board[i][col].p.type == PieceType.QUEEN)&& MainCLI.board[i][col].p.color != color) {
                    pieceCoordinates[0] = i;
                    pieceCoordinates[1] = col;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
                }
            }
        }

        //to the left
        for (int i = col - 1; i >= 0; i--) {

            if (col - 1 < 0) {
                break;
            }

            if (MainCLI.board[row][i].p != null && MainCLI.board[row][i].p.type != PieceType.NONE) {
                if ((MainCLI.board[row][i].p.type == PieceType.ROOK || MainCLI.board[row][i].p.type == PieceType.QUEEN) && MainCLI.board[row][i].p.color != color) {
                    pieceCoordinates[0] = row;
                    pieceCoordinates[1] = i;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
                }
            }
        }

        // to the right
        for (int i = col + 1; i < 8; i++) {

            if (col + 1 > 7) {
                break;
            }

            if (MainCLI.board[row][i].p != null && MainCLI.board[row][i].p.type != PieceType.NONE) {
                if ((MainCLI.board[row][i].p.type == PieceType.ROOK || MainCLI.board[row][i].p.type == PieceType.QUEEN) && MainCLI.board[row][i].p.color != color) {
                    pieceCoordinates[0] = row;
                    pieceCoordinates[1] = i;
                    return pieceCoordinates;
                }
                //we found a different type of piece and we cannot jump over it because this is a rook
                else {
                    break;
                }
            }
        }
        //<---------------------------------------HORIZONTAL/VERTICAL---------------------------------------->

        //<---------------------------------KNIGHT-------------------------------------------->
        //up
        if (row >= 2) {
            if (col > 0) {
                if (MainCLI.board[row - 2][col - 1].p.type == PieceType.KNIGHT && MainCLI.board[row - 2][col - 1].p.color != color) {
                    pieceCoordinates[0] = row - 2;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }
            }
            if(col < 7) {
                if (MainCLI.board[row - 2][col + 1].p.type == PieceType.KNIGHT && MainCLI.board[row - 2][col + 1].p.color != color) {
                    pieceCoordinates[0] = row - 2;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }
        }

        //down
        if (row <= 5) {
            if (col > 0) {

                if (MainCLI.board[row + 2][col - 1].p.type == PieceType.KNIGHT && MainCLI.board[row + 2][col - 1].p.color != color) {
                    pieceCoordinates[0] = row + 2;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }

            }
            if(col < 7) {
                if (MainCLI.board[row + 2][col + 1].p.type == PieceType.KNIGHT && MainCLI.board[row + 2][col + 1].p.color != color) {
                    pieceCoordinates[0] = row + 2;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }
        }

        //left
        if (col >= 2) {
            if (row > 0) {
                if (MainCLI.board[row - 1][col - 2].p.type == PieceType.KNIGHT && MainCLI.board[row - 1][col - 2].p.color != color) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col - 2;
                    return pieceCoordinates;
                }
            }
            if(row < 7) {
                if (MainCLI.board[row + 1][col - 2].p.type == PieceType.KNIGHT && MainCLI.board[row + 1][col - 2].p.color != color) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col - 2;
                    return pieceCoordinates;
                }
            }
        }

        //right
        if (col <= 5) {
            if (row > 0) {
                if (MainCLI.board[row - 1][col + 2].p.type == PieceType.KNIGHT && MainCLI.board[row - 1][col + 2].p.color != color) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col + 2;
                    return pieceCoordinates;
                }
            }
            if(row < 7) {
                if (MainCLI.board[row + 1][col + 2].p.type == PieceType.KNIGHT && MainCLI.board[row + 1][col + 2].p.color != color) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col + 2;
                    return pieceCoordinates;
                }
            }
        }

        //<----------------------KNIGHT------------------------------------------------->>>>>>


        //<-------------------------DIAGONAL--------------------------------------------->
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
                    if ((MainCLI.board[i][j].p.type == PieceType.BISHOP || MainCLI.board[i][j].p.type == PieceType.QUEEN) && MainCLI.board[i][j].p.color != color) {
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
                    if ((MainCLI.board[i][j].p.type == PieceType.BISHOP || MainCLI.board[i][j].p.type == PieceType.QUEEN) && MainCLI.board[i][j].p.color != color) {
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
                    if ((MainCLI.board[i][j].p.type == PieceType.BISHOP || MainCLI.board[i][j].p.type == PieceType.QUEEN) && MainCLI.board[i][j].p.color != color) {
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
                    if ((MainCLI.board[i][j].p.type == PieceType.BISHOP || MainCLI.board[i][j].p.type == PieceType.QUEEN) && MainCLI.board[i][j].p.color != color) {
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
        //<-------------------------DIAGONAL--------------------------------------------->

        //PAWN - opposites because check
        if (color == PieceColor.BLACK) {
            //value needs to be one more or one less to be able to capture as a pawn
            //plus because down the board
            if (row < 7 && col > 0) {
                if (MainCLI.board[row + 1][col - 1].p.type == PieceType.PAWN && MainCLI.board[row + 1][col - 1].p.color == PieceColor.WHITE) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }
            }

            if (row < 7 && col < 7) {
                if (MainCLI.board[row + 1][col + 1].p.type == PieceType.PAWN && MainCLI.board[row + 1][col + 1].p.color == PieceColor.WHITE) {
                    pieceCoordinates[0] = row + 1;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }
        }
        else if (color == PieceColor.WHITE) {
            if (row > 0 && col > 0) {
                if (MainCLI.board[row - 1][col - 1].p.type == PieceType.PAWN && MainCLI.board[row - 1][col - 1].p.color == PieceColor.BLACK) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col - 1;
                    return pieceCoordinates;
                }
            }

            if (row > 0 && col < 7) {
                if (MainCLI.board[row - 1][col + 1].p.type == PieceType.PAWN && MainCLI.board[row - 1][col + 1].p.color == PieceColor.BLACK) {
                    pieceCoordinates[0] = row - 1;
                    pieceCoordinates[1] = col + 1;
                    return pieceCoordinates;
                }
            }
        }

        return null;
    }

    boolean canMove(int row, int col, PieceColor color) {
        //have to check if the king can move
        //write a can move function, is there a square that is NOT empty, not in check, around the king
        //use current check function - write it in the king class.

        //off of the top edge of the board
        if(row > 0) {

            //top left
            if (col > 0) {
                //checks if there is not a piece there and that the square is not in check
                if (MainCLI.board[row - 1][col - 1].p.type == PieceType.NONE && !check(row - 1, col - 1, color)) { //MainCLI.board[row - 1][col - 1].p.color == color) {
                    return true;
                }
            }

            //top middle
            if (MainCLI.board[row - 1][col].p.type == PieceType.NONE && !check(row - 1, col, color)) { //MainCLI.board[row - 1][col - 1].p.color == color) {
                return true;
            }

            if (col < 7) {
                //top right
                if (MainCLI.board[row - 1][col + 1].p.type == PieceType.NONE && !check(row - 1, col + 1, color)) { //MainCLI.board[row - 1][col - 1].p.color == color) {
                    return true;
                }
            }


        }

        //off the left edge of the board
        if(col > 0) {
            //middle left
            if (MainCLI.board[row][col - 1].p.type == PieceType.NONE && !check(row, col - 1, color)) { //MainCLI.board[row - 1][col - 1].p.color == color) {
                return true;
            }
        }

        //off the right edge of the board
        if(col < 7) {
            //middle right
            if (MainCLI.board[row][col + 1].p.type == PieceType.NONE && !check(row, col + 1, color)) { //MainCLI.board[row - 1][col - 1].p.color == color) {
                return true;
            }
        }

        //off the bottom edge of the board
        if(row < 7) {

            if (col > 0) {
                //bottom left
                if (MainCLI.board[row + 1][col - 1].p.type == PieceType.NONE && !check(row + 1, col - 1, color)) { //MainCLI.board[row - 1][col - 1].p.color == color) {
                    return true;
                }
            }

            //bottom middle
            if (MainCLI.board[row + 1][col].p.type == PieceType.NONE && !check(row + 1, col, color)) { //MainCLI.board[row - 1][col - 1].p.color == color) {
                return true;
            }

            if (col < 7) {
                //bottom right
                if (MainCLI.board[row + 1][col + 1].p.type == PieceType.NONE && !check(row + 1, col + 1, color)) { //MainCLI.board[row - 1][col - 1].p.color == color) {
                    return true;
                }
            }
        }

        return false;
    }

    //checks is our move gets us out of a check by capturing
    //pre: the move has already been validated
    //only thing it has not been validated for is if the move causes a check...
    boolean captureOutOfCheck(int moveRow, int moveCol, PieceType movePieceType, int checkPieceRow, int checkPieceCol, PieceColor color) {

        //can simply call is valid for each of these I think
        //could also compare rows and columns, not the same type of logic as is valid i dont think
        if (moveRow == checkPieceRow && moveCol == checkPieceCol) {
            return true;
        }
        return false;
    }

    boolean moveOutOfCheck(int moveRow, int moveCol, PieceColor color) {
        //if that square is in check then we can't move out of it
        return !(check(moveRow, moveCol, color));
    }

    //does our move block check
    boolean blockCheck(int checkingPieceRow, int checkingPieceCol, PieceColor checkingPieceColor,
                       PieceType checkingPieceType, int blockingSquareRow, int blockingSquareCol, int movingPieceRow,
                       int movingPieceCol, PieceType movingPieceType, PieceColor movingPieceColor, int kingRow, int kingCol) {

        //prelim checking
        if(checkingPieceType == PieceType.PAWN || checkingPieceType == PieceType.KNIGHT || movingPieceType == PieceType.KING) {
            return false;
        }

        boolean rookQueen = false;
        boolean bishopQueen = false;
        if (checkingPieceType == PieceType.QUEEN) {
            //same row or col with a queen having check means has to be horizontal or vertical
            if (checkingPieceRow == kingRow || checkingPieceCol == kingCol) {
                rookQueen = true;
            }
            else {
                bishopQueen = true;
            }
        }

        if (checkingPieceType == PieceType.ROOK || rookQueen) {
            //now we need to compare things:
            //below the king
            if (checkingPieceRow > kingRow) {
                //precondition is that this move is fine I'm pretty sure...
                if (blockingSquareCol == kingCol && (blockingSquareRow < checkingPieceRow && blockingSquareRow > kingRow)) {
                    return true;
                }
            }
            //above the King
            else if (checkingPieceRow < kingRow) {
                if (blockingSquareCol == kingCol && (blockingSquareRow > checkingPieceRow && blockingSquareRow < kingRow)) {
                    return true;
                }
            }
            //same row
            //different cols
            //to the right
            else if (checkingPieceCol > kingCol) {
                if (blockingSquareRow == kingRow && (blockingSquareCol < checkingPieceCol && blockingSquareCol > kingCol)) {
                    return true;
                }
            }
            //to the left
            else if (checkingPieceCol < kingCol) {
                if (blockingSquareRow == kingRow && (blockingSquareCol > checkingPieceCol && blockingSquareCol < kingCol)) {
                    return true;
                }
            }


        }
        else if (checkingPieceType == PieceType.BISHOP || bishopQueen) {
            //do four quadrents bc we know its check
            //have to figure out how to do the inbetween
            //has to be an equal amount between the squares
            //like + i for both or smth... calc the difference and iterate?

            //bottom right of king in terms of diags
            if (checkingPieceRow > kingRow && checkingPieceCol > kingCol) {

                int j = checkingPieceCol + 1;
                //starts on square diagnol bottom right of king
                for(int i = kingRow + 1; i < checkingPieceRow; i++) {

                    if (blockingSquareRow == i && blockingSquareCol == j) {
                        return true;
                    }

                    if (j == checkingPieceCol || j >= 7 || i >= 7) {
                        return false;
                    }
                    j++;
                }
            }
            //top left of king in terms of diags
            else if (checkingPieceRow < kingRow && checkingPieceCol < kingCol) {

                int j = checkingPieceCol - 1;
                //starts on square diagnol top left of king
                for(int i = kingRow - 1; i > checkingPieceRow; i--) {

                    if (blockingSquareRow == i && blockingSquareCol == j) {
                        return true;
                    }

                    if (j == checkingPieceCol || j <= 0 || i <= 0) {
                        return false;
                    }
                    j--;
                }
            }
            //top right of king in terms of diags
            // col >
            //row <
            else if (checkingPieceRow < kingRow && checkingPieceCol > kingCol) {

                int j = checkingPieceCol + 1;
                //starts on square diagnol bottom right of king
                for(int i = kingRow - 1; i > checkingPieceRow; i--) {

                    if (blockingSquareRow == i && blockingSquareCol == j) {
                        return true;
                    }

                    if (j == checkingPieceCol || j >= 7 || i <= 0) {
                        return false;
                    }
                    j++;
                }
            }
            //bottom left
            //row >
            //col <
            else if (checkingPieceRow > kingRow && checkingPieceCol < kingCol) {

                int j = checkingPieceCol - 1;
                //starts on square diagnol top left of king
                for(int i = kingRow + 1; i < checkingPieceRow; i++) {

                    if (blockingSquareRow == i && blockingSquareCol == j) {
                        return true;
                    }

                    if (j == checkingPieceCol || j <= 0 || i >= 7) {
                        return false;
                    }
                    j--;
                }
            }
        }

        return false;

    }

    /*
    * if (movePieceType == PieceType.ROOK) {

        }
        else if (movePieceType == PieceType.KNIGHT) {

        }
        else if (movePieceType == PieceType.BISHOP) {

        }
        else if (movePieceType == PieceType.QUEEN) {

        }
        else if (movePieceType == PieceType.KING) {

        }
        else if (movePieceType == PieceType.PAWN) {

        }
    *
    *
    *
    *
    * */


}

//if king wants to move to a square or capture
//check if that square is in check
//iterate in each direction to find a piece that could check...
//opposite color and can move means the king is in check
//if that squarer is in check then cant move or capture to it
//have to do all nine directions and knight moves
//make sure to check board boundaries
//check method should take a square
//move method should call chekc method and check if king is aorund the 9 squares
//todo track king position in MainCLI class - so then can just know where it is...

//For checking for check on each turn
//check if move is valid first, then make a copy of the board with the move on it...
//pass an array of type squares to function
//modify that array (arrays are passed by reference so should modify the original as well)
//make the "valid" move in that array...
//check the king whose turn it was first... - white turn check white king - ensure that the king is not in check
//if the king is in check then revert the move
//if king is not in check - then edit the original board using the move function
//then check Black's king and see if they are in check... - if they are output a message letting them know

//TODO: am I going to enforce proper notation with + and # for check and mate?
//TODO: need to decide how I would like to handle checkmate chekcing
//if check - check for possible moves - if find one//
//could start with iterate from checking piece and look for captures
//iterate from squares between the piece and king and look for blocks
//then check moves?

