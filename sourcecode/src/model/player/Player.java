package model.player;

import java.util.List;

import model.board.Board;
import model.board.Cell;
import model.board.HalfCircle;
import model.board.Square;
import model.gem.*;

public class Player { //set action with board
    private int score1 = 0;
    private int score2 = 0;
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
        return sum;
    }
    
    public void computeScore(String player, int earnedScore){
        if (player.equals(player1)){
            this.score1 = this.score1 + earnedScore;
        } 
        else if (player.equals(player2)){
            this.score2 = this.score2 + earnedScore;
        }
    }

    public void reduceScore(String player){
        if (player.equals(player1)){
            this.score1 = this.score1 - 5;
            for (int i = 0; i< 5; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+1].addGem(smallGem);
            }
        } 
        else if (player.equals(player2)){
            this.score2 = this.score2 - 5;
            for (int i = 0; i< 5; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+board.getNumSquares()/2+2].addGem(smallGem);
            }
        }
    }
    

    public int getScore(String player){
        if (player.equals(player1)){
            return this.score1;
        }
        else if (player.equals(player2)){
            return this.score2;
        }
        return 0;
    }


    public void spreadGems(String player, Cell cellChosen, int direction){
            Cell stopCell;
            int locationChosen = cellChosen.getLocation();
            List<Gem> gemList = cellChosen.getGemList();
            int numberOfGems = gemList.size();
            int totalCell = board.getCells().length;

            if (direction == 1){ //clockwise
                
                //spread first round
                for (int i = 0; i < numberOfGems; i++) {
                    int index = (locationChosen + i + 1) % totalCell; // Calculate the index correctly
                    board.getCells()[index].addGem(gemList.get(i));
                }
                cellChosen.setEmpty();

                //check contuinity
                stopCell = board.getCells()[(locationChosen + numberOfGems) % totalCell];
                Cell nextStopCell = board.getNextCellClockwise(stopCell);
                if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof Square)){
                    spreadGems(player,nextStopCell, direction);
                    // nextStopCell.setEmpty();
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
                    // System.out.println(earnedScore);
                    earnedCell.setEmpty();
                    computeScore(player,earnedScore);
                    nextStopCell = board.getNextCellClockwise(earnedCell);
                    //switch turn
                    }

                }
    
            }

            else if (direction == 0) { //counter clockwise
    
                //spread first round
                for (int i = 0; i < numberOfGems; i++) {
                    int index = (locationChosen - i - 1 + totalCell) % totalCell; // Calculate the index correctly
                    board.getCells()[index].addGem(gemList.get(i));
                }
                cellChosen.setEmpty();

                //check contuinity
                stopCell = board.getCells()[(locationChosen - numberOfGems + totalCell) % totalCell];
                Cell nextStopCell = board.getNextCellCounterClockwise(stopCell);
                if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof Square)) {
                    spreadGems(player, nextStopCell, direction);
                    // nextStopCell.setEmpty();
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
                            earnedCell.setEmpty();
                            // System.out.println(earnedScore);
                            this.computeScore(player, earnedScore);
                            nextStopCell = board.getNextCellCounterClockwise(earnedCell);

                        }
                        //switch turn
                    }
                }
            }
        }

        public void assembleSmallGems(){
            
            for (int i = 0; i < board.getNumSquares()/2; i++){
                Cell cell1 = board.getPlayer1Cells()[i];
                int earnedScore1 = earnScore(cell1);
                cell1.setEmpty();
                this.computeScore(this.player1, earnedScore1);

                Cell cell2 = board.getPlayer2Cells()[i];
                int earnedScore2 = earnScore(cell2);
                cell2.setEmpty();
               
                this.computeScore(this.player2, earnedScore2);
                
            }
            
           
        }

    }
    



    


