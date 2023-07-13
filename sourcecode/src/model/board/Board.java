package model.board;

import model.gem.*;


public class Board {
    private final int numSquares = 10;
    private final int numHalfCircles = 2;
    private final int numBigGems = 2;
    private final int numSmallGems = 50;
    private Cell[] cells; // list of all cells, use array to make it easier to access with index
    private Cell[] player1Cells =  new Cell[numSquares/2]; // list of cells on player 1 side
    private Cell[] player2Cells =  new Cell[numSquares/2]; // list of cells on player 2 side

    public int getNumSquares() {
        return numSquares;
    }

    public int getNumHalfCircles() {
        return numHalfCircles;
    }

    public int getNumSmallGems() {
        return numSmallGems;
    }

    public int getNumBigGems() {
        return numBigGems;
    }

    public Board() {
        initializeCells();
        addGemsToCells();
        setCellOnPlayer1();
        setCellOnPlayer2();
    }

    private void initializeCells() {
        cells = new Cell[numSquares + numHalfCircles];
        cells[0] = new HalfCircle(0, numBigGems / 2);
        cells[(numSquares + numHalfCircles) / 2] = new HalfCircle((numSquares + numHalfCircles) / 2, numBigGems / 2);
    }

    private void addGemsToCells() {
        cells[0].addGem(new BigGem(cells[0]));

        for (int i = 1; i <= numSquares / 2; i++) {
            cells[i] = new Square(i, numSmallGems / numSquares);
            for (int j = 0; j < cells[i].getNumberOfGems(); j++) {
                cells[i].addGem(new SmallGem(cells[i]));
            }
        }

        cells[(numSquares + numHalfCircles) / 2].addGem(new BigGem(cells[(numSquares + numHalfCircles) / 2]));

        for (int i = numSquares / 2 + 2; i <= numSquares + numHalfCircles - 1; i++) {
            cells[i] = new Square(i, numSmallGems / numSquares);
            for (int j = 0; j < cells[i].getNumberOfGems(); j++) {
                cells[i].addGem(new SmallGem(cells[i]));
            }
        }
    }

    public Cell[] getBoard() {
        return cells;
    }

    public Cell getNextCellCounterClockwise(Cell cell) {
        int position = cell.getLocation();
        int lastPosition = numSquares + numHalfCircles - 1;
        return cells[position == 0 ? lastPosition : position - 1];
    }

    public Cell getNextCellClockwise(Cell cell) {
        int position = cell.getLocation();
        int lastPosition = numSquares + numHalfCircles - 1;
        return cells[position == lastPosition ? 0 : position + 1];
    }

    public void setCellOnPlayer1() {
        for (int i = 1; i <= this.getNumSquares() / 2; i++) {
            player1Cells[i-1] = this.getBoard()[i];
        }
    }

    public void setCellOnPlayer2() {
        for (int i = this.getNumSquares() / 2 + 2; i <= this.getNumSquares() + this.getNumHalfCircles() - 1; i++) {
            player2Cells[i-7] = this.getBoard()[i];
        }
    }
    public Cell[] getPlayer1Cells() {
        return player1Cells;
    }

    public Cell[] getPlayer2Cells() {
        return player2Cells;
    }
    //need to check again
    public boolean endGame(){
        //check big gem in half circle
        Cell[] cells = this.getBoard();

        for (Gem gem :cells[0].getGemList()){
            if (gem instanceof BigGem){
                return false;
            }
        }

        for (Gem gem :cells[12].getGemList()){
            if (gem instanceof BigGem){
                return false;
            }
        }
        
        return true;
    }

}
