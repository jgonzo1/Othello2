import java.util.ArrayList;
import java.util.List;

public class Gameboard {

    private int HEIGHT = 10;
    private int WIDTH = 10;
    private Cell [][] board = new Cell[HEIGHT][WIDTH];
    private String myColor;
    private String oppColor;
    private int currenty;
    private int currentx;
    private int playablex;
    private int playabley;
    private int player;
    private int count;
    private boolean passed;
    private boolean legal = false;
    private boolean endgame = false;
    private List<Index> candidates = new ArrayList<>();
    Direction dir;


    public Gameboard() {
        myColor = "C not set";
        oppColor = "C not set";
    }

    public boolean isEndgame() {
        return endgame;
    }

    public void endGame() {
        endgame = true;
    }

    public String getMyColor() {
        return myColor;
    }

    public void setMyColor(String myColor) {
        this.myColor = myColor;
    }

    //return if the piece is the edge
    public boolean isValid(Index index) {
        if (board[index.getColumn()][index.getRow()].getCellvalue() == 0 || board[index.getColumn()][index.getRow()].isBorder()) {
            return false;
        }
        return true;
    }

    public void getLegalMoves() {
        for (int i = 0; i < HEIGHT; i++ ) {
            for (int j= 0; j < WIDTH; j++ ) {
                if (board[i][j].isMe()) {
                    player = board[i][j].getCellvalue();
                    Index index = new Index(j, i);
                    Direction [] direction = Direction.values();
                    for (Direction d : direction) {
                        Index current = d.next(index);
                        if (isValid(current)) {
                            if (!(board[current.getColumn()][current.getRow()].getCellvalue() == player)) {
                                inspectLegal(current, player, d);
                            }
                        }
                    }

                }
            }
        }

    }

    public boolean isPassed() {
        return passed;
    }

    public void inspectLegal(Index index, int player, Direction d) {
        passed = false;
        Index current = d.next(index);
        if (board[current.getColumn()][current.getRow()].isBorder()) {

        } else if (board[current.getColumn()][current.getRow()].getCellvalue() == 0) {
            currentx = current.getRow();
            currenty = current.getColumn();
            System.out.println("C " + ((char) (currentx + 96)) + " " + currenty + " is a valid move");
            playablex = currentx;
            playabley = currenty;
        } else {
            if (isValid(current)) {
                if (!(board[current.getColumn()][current.getRow()].getCellvalue() == player)) {
                    inspectLegal(current, player, d);
                }
            } else {
                System.out.println("C No legal move pass");
                System.out.println(myColor);
                passed = true;
            }
        }


    }

    public void inspectPiece(Index index, int player, Direction d) {

        Index current = d.next(index);
        if (player == board[index.getColumn()][index.getRow()].getCellvalue()) {
            //all pieces should be flipped
        } else if (board[current.getColumn()][current.getRow()].getCellvalue() == 0) {
            candidates.clear();
        } else {
            if (isValid(current)) {
                candidates.add(new Index(current.getRow(), current.getColumn()));
                current = d.next(current);
                inspectPiece(current, player, d);
            }
        }

    }

    public void flipPieces(Index index) {
        player = board[index.getColumn()][index.getRow()].getCellvalue();

        Direction [] direction = Direction.values();
        for (Direction d : direction) {
            candidates.clear();
            Index current = d.next(index);

            if (isValid(current)) {
                candidates.add(new Index(current.getRow(), current.getColumn()));
                current = d.next(current);
                if (!board[current.getColumn()][current.getRow()].isBorder()) {
                    inspectPiece(current, player, d);
                }


                for (Index c : candidates) {
                    // philip
                    if (player == -1) {
                        board[c.getColumn()][c.getRow()].setOPPONENT(oppColor);
                    } else if (player == 1) {
                        board[c.getColumn()][c.getRow()].setME(myColor);
                    }
                }
            }
        }
    }

    public void playOpponentMove(int x, int y) {
        board[y][x].setOPPONENT(oppColor);
        System.out.println("C flipping");
        printBoard();
        Index index = new Index(x, y);
        flipPieces(index);
        printBoard();
    }

    public void playMyMove(int x, int y) {
        board[y][x].setME(myColor);
        System.out.println("C flipping");
        System.out.println("" + myColor + " "+ ((char) (x + 96)) + " " + y);
        printBoard();
        Index index = new Index(x, y);
        flipPieces(index);
        printBoard();
    }

    public void printBoard() {
        for (int i = 0; i < HEIGHT; i++ ) {
            System.out.println();
            System.out.print("C ");
            for (int j = 0; j < WIDTH; j++ ) {
                System.out.print(board[i][j] + " ");
            }
        }
        System.out.println();
    }

    public int getPlayablex() {
        return playablex;
    }

    public int getPlayabley() {
        return playabley;
    }



    //initialize board
    public void initialize(String color) {

        //assign mycolors
        if (color.compareTo("I B") == 0) {
            myColor = "B";
            oppColor = "W";
        } else if (color.compareTo("I W") == 0) {
            myColor = "W";
            oppColor = "B";
        }

        int counter2 = 48;
        boolean vertical = false;
        for (int i = 0; i < HEIGHT; i++ ) {
            int counter = 97;
            if (i >= 1) {
                vertical = true;
                counter2++;
            }
            for (int j = 0; j < WIDTH; j++ ) {
                board[i][j] = new Cell();

                //set borders
                if (i == 0 || j == 0 || i == 9 || j == 9) {
                    board[i][j].setBorder((char) counter);
                    counter++;
                    if ( (i == 0 && j == 0) || (i == 0 && j == 9) ||
                            (i == 9 && j == 0) || (i == 9 && j == 9) ) {
                        board[i][j].setBorder('#');
                        counter--;
                    }

                    if (vertical) {
                        board[i][j].setBorder((char) counter2);
                        if (j == 9 || (i == 9)) {
                            board[i][j].setBorder('#');
                        }
                    }

                }
            }

        }

        //initialize starting pieces
        if (myColor.compareTo("B") == 0) {
            board[4][4].setOPPONENT(oppColor);
            board[5][5].setOPPONENT(oppColor);
            board[4][5].setME(myColor);
            board[5][4].setME(myColor);
        } else if (myColor.compareTo("W") == 0) {
            board[4][4].setME(myColor);
            board[5][5].setME(myColor);
            board[4][5].setOPPONENT(oppColor);
            board[5][4].setOPPONENT(oppColor);
        }

        printBoard();
        System.out.print("R " + myColor);
        System.out.println();
    }



}
