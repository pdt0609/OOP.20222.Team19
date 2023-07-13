package model.player;

import model.board.Board;
import model.board.Cell;
import model.board.HalfCircle;
import model.board.Square;
import model.gem.Gem;

public class Player { //set action with board
    private static int score1 = 0;
    private static int score2 = 0;
    private String player1;
    private String player2;
    private int turn;
    private Cell cellChosen;
    private int direction;
    private Board board;
    
    public Player(String player1, String player2, Board board){ // need to construct player vs board at first
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public Cell getCellChosen(){
        return cellChosen;
    }

    public void setCellChosen(Cell cellChosen){
        this.cellChosen = cellChosen;
    }

    public int getTurn(){
        
        return turn;
    }
    
    public void setTurn(int turn){
        this.turn = turn;
    }

    
    public void switchTurn(){
        if (this.getTurn() == 1){
            this.setTurn(2);
        }
        else{
            this.setTurn(1);
        }
    }

    public int earnScore(Cell earnedCell) {
        int sum = 0;
        int N = earnedCell.getGemList().size();
        for (int i = 0; i < N; i++) {
            Gem gem = earnedCell.getGemList().get(i);
            if (gem != null) { // why have null
                sum += gem.getValue();
            }
        }
        earnedCell.setEmpty();
        return sum;
    }
    

    public void computeScore(String player, int earnedScore){
        if (player.equals(player1)){
            score1 = score1 + earnedScore;
        } 
        else if (player.equals(player2)){
            score2 = score2 + earnedScore;
        }
    }

    public int getScore(String player1){
        if (player1.equals(player1)){
            return score1;
        }
        else if (player1.equals(player2)){
            return score2;
        }
        return 0;
    }

    public void spreadGems(String player, Cell cellChosen, int direction){
        Cell stopCell = null;
        

            int locationChosen = cellChosen.getLocation();
            if (direction == 1){ //clockwise
                
                //spread first round
                for (int i = locationChosen + 1; i <= locationChosen + 5; i++) {
                    int index = i % board.getBoard().length; // Wrap around the index
                    Gem movedGem = cellChosen.moveGem();
                    board.getBoard()[index].addGem(movedGem);
                }
                cellChosen.setEmpty();

                //check contuinity
                stopCell = board.getBoard()[(locationChosen + 5)% board.getBoard().length];
                Cell nextStopCell = board.getNextCellClockwise(stopCell);
                if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof Square)){
                    spreadGems(player,nextStopCell, direction);
                    nextStopCell.setEmpty();
                }
                else if ((nextStopCell.isEmpty()) && (board.getNextCellClockwise(nextStopCell).isEmpty())){

                }
                else if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof HalfCircle)){

                    //switch turn
                }
                else{
                    while ((nextStopCell.isEmpty()) && !(board.getNextCellClockwise(nextStopCell).isEmpty()) ){
                    Cell earnedCell = board.getNextCellClockwise(nextStopCell);
                    int earnedScore = earnScore(earnedCell);
                    computeScore(player,earnedScore);
                    nextStopCell = board.getNextCellClockwise(earnedCell);
                    //switch turn
                    }

                }
               
            }

            else if (direction == 0) { //counter clockwise
                // Cell stopCell;
                //spread first round
                for (int i = locationChosen - 1; i > locationChosen - 6; i--) {
                    if (i >= 0) {
                        Gem movedGem = cellChosen.moveGem();
                        board.getBoard()[i].addGem(movedGem);
                    }
                    else{
                        int index = board.getNumHalfCircles() + board.getNumSquares() + i;
                        Gem movedGem = cellChosen.moveGem();
                        board.getBoard()[index].addGem(movedGem);
                    }
                    
                }

                cellChosen.setEmpty();
                //check contuinity
                if (locationChosen - 5 >= 0){
                    stopCell = board.getBoard()[locationChosen - 5];
                }
                else{
                    int index = board.getNumHalfCircles() + board.getNumSquares() + locationChosen - 5;
                    stopCell = board.getBoard()[index];
                }
                
                Cell nextStopCell = board.getNextCellCounterClockwise(stopCell);
                if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof Square)) {
                    spreadGems(player, nextStopCell, direction);
                    nextStopCell.setEmpty();
                } else if ((nextStopCell.isEmpty()) && (board.getNextCellCounterClockwise(nextStopCell).isEmpty())) {

                    //switch turn
                } else if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof HalfCircle)) {

                    //switch turn
                }
                else{
                    while ((nextStopCell.isEmpty()) && !(board.getNextCellCounterClockwise(nextStopCell).isEmpty())) {
                        Cell earnedCell = board.getNextCellCounterClockwise(nextStopCell);
                        if (earnedCell.getGemList().size() > 0){
                            int earnedScore = earnScore(earnedCell);
                            computeScore(player, earnedScore);
                            nextStopCell = board.getNextCellCounterClockwise(earnedCell);

                        }
                        //switch turn
                    }
                }
            }
        }

    }
    



    


