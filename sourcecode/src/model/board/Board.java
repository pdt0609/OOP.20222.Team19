package model.board;

import model.gem.*;

public class Board { // is composed of cells, it can interact with cells/all method with cell
    private final int numSquares = 10; // composition
    private final int numHalfCircles = 2;
    private final int numBigGems = 2;
    private final int numSmallGems = 50;
    private Cell[] cells; // array of all cells, use array to make it easier to access with index
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

    public Board() { // construct board
        initializeCells();
        addGemsToCells();
        setCellOnPlayer1();
        setCellOnPlayer2();
    }

    private void initializeCells() {
        cells = new Cell[this.numSquares + this.numHalfCircles];
        cells[0] = new HalfCircle(0, numBigGems / 2);
        cells[(this.numSquares + this.numHalfCircles) / 2] = new HalfCircle((this.numSquares + this.numHalfCircles) / 2, numBigGems / 2);
    }

    private void addGemsToCells() {

        Gem bigGem1 = new BigGem(); // polymorphism for declaring
        this.cells[0].addGem(bigGem1);

        for (int i = 1; i <= numSquares / 2; i++) {
            this.cells[i] = new Square(i, this.numSmallGems / this.numSquares);
            for (int j = 0; j < this.cells[i].getNumberOfGems(); j++) {
                Gem smallGem = new SmallGem();
                this.cells[i].addGem(smallGem);
            }
        }

        Gem bigGem2 = new BigGem();
        this.cells[(this.numSquares + this.numHalfCircles) / 2].addGem(bigGem2);

        for (int i = numSquares / 2 + 2; i <= numSquares + numHalfCircles - 1; i++) {
            this.cells[i] = new Square(i, this.numSmallGems / this.numSquares);
            for (int j = 0; j < cells[i].getNumberOfGems(); j++) {
                Gem smallGem = new SmallGem();
                this.cells[i].addGem(smallGem);
            }
        }
    }

    public Cell[] getCells() { // get cells of board
        return cells;
    }

    public Cell getNextCellCounterClockwise(Cell cell) {
        int position = cell.getLocation();
        int lastPosition = this.numSquares + this.numHalfCircles - 1;
        return this.cells[position == 0 ? lastPosition : position - 1];
    }

    public Cell getNextCellClockwise(Cell cell) {
        int position = cell.getLocation();
        int lastPosition = this.numSquares + this.numHalfCircles - 1;
        return this.cells[position == lastPosition ? 0 : position + 1];
    }

    public void setCellOnPlayer1() {
        for (int i = 1; i <= this.getNumSquares() / 2; i++) {
            this.player1Cells[i-1] = this.getCells()[i];
        }
    }

    public void setCellOnPlayer2() {
        for (int i = this.getNumSquares() / 2 + 2; i <= this.getNumSquares() + this.getNumHalfCircles() - 1; i++) {
            this.player2Cells[i-7] = this.getCells()[i];
        }
    }

    public Cell[] getPlayer1Cells() {
        return player1Cells;
    }

    public Cell[] getPlayer2Cells() {
        return player2Cells;
    }

    //Condition for ending game
    public boolean endGame(){
        //check big gem in half circle
        Cell[] cells = this.getCells();

        for (Gem gem :cells[0].getGemList()){
            if (gem instanceof BigGem){
                return false;
            }
        }

        for (Gem gem :cells[6].getGemList()){
            if (gem instanceof BigGem){
                return false;
            }
        }

        return true;
    }
}
