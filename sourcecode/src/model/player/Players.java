package model.player;

import java.util.ArrayList;
import java.util.List;

import model.board.Board;
import model.board.Cell;
import model.board.Pickable;
import model.gem.*;

public class Players { //set action of players with board. Do not make for each player, we set for all players -> Easy to keep track turn. Do not use inheritance here because children are actually instances of the same class
    private int score1 = 0;
    private int score2 = 0;
    private String player1;
    private String player2;
    private int turn;
    private Cell cellChosen;
    private int direction;
    private Board board;
    private List<Cell> itinerary = new ArrayList<Cell>(); 
    
    public Players(String player1, String player2, Board board){ // need to construct player vs board at first
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

    public String getPlayer1(){
        return player1;
    }

    public String getPlayer2(){
        return player2;
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

    public int earnScore(Cell earnedCell) {
        int sum = 0;
        int N = earnedCell.getGemList().size();
        for (int i = 0; i < N; i++) {
            Gem gem = earnedCell.getGemList().get(i);
            sum += gem.getValue();
        }
        return sum;
    }
    
    public void computeScore(String player, int earnedScore){
        if (player.equals(this.getPlayer1())){
            this.score1 = this.score1 + earnedScore;
        } 
        else if (player.equals(this.getPlayer2())){
            this.score2 = this.score2 + earnedScore;
        }
    }

    public void reduceScore(String player){
        if (player.equals(this.getPlayer1())){
            this.score1 = this.score1 - 5;
            for (int i = 0; i< 5; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+1].addGem(smallGem);
            }
        } 
        else if (player.equals(this.getPlayer2())){
            this.score2 = this.score2 - 5;
            for (int i = 0; i< 5; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+board.getNumSquares()/2+2].addGem(smallGem);
            }
        }
    }
    
    public int getScore(String player){
        if (player.equals(this.getPlayer1())){
            return this.score1;
        }
        else if (player.equals(this.getPlayer2())){
            return this.score2;
        }
        return 0;
    }

    public void setItinerary(List<Cell> itinerary){
        this.itinerary = itinerary;
    }

    public List<Cell> getItinerary(){
        return itinerary;
    }
    public void assembleSmallGems(){

    for (int i = 0; i < board.getNumSquares()/2; i++){
        Cell cell1 = board.getPlayer1Cells()[i];
        int earnedScore1 = earnScore(cell1);
        cell1.setEmpty();
        this.computeScore(this.getPlayer1(), earnedScore1);

        Cell cell2 = board.getPlayer2Cells()[i];
        int earnedScore2 = earnScore(cell2);
        cell2.setEmpty();
        this.computeScore(this.getPlayer2(), earnedScore2);
        }
    }
    


    public void spreadGems(String player, Cell cellChosen, int direction){
            Cell stopCell;
            int locationChosen = cellChosen.getLocation();
            List<Gem> gemList = cellChosen.getGemList(); //pass by reference
            int numberOfGems = gemList.size();
            int totalCell = board.getCells().length;

            if (cellChosen.isEmpty()) {
                // switch turn
                return;
            }

            if (direction == 1){ //clockwise
                
                //spread first round
                
                for (int i = 0; i < numberOfGems; i++) {
                    int index = (locationChosen + i + 1) % totalCell; 
                    // Calculate the index correctly
                    Cell nextCell = board.getCells()[index];
                    nextCell.addGem(gemList.get(i));
                    Cell copyNextCell = nextCell.copyCell();
                    itinerary.add(copyNextCell);
                }
                cellChosen.setEmpty();
                Cell copyCellChosen = cellChosen.copyCell();
                int itinerarySize = itinerary.size();
                itinerary.add(itinerarySize-numberOfGems,copyCellChosen);
                

                //check contuinity
                stopCell = board.getCells()[(locationChosen + numberOfGems) % totalCell];
                Cell nextStopCell = board.getNextCellClockwise(stopCell);
                if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof Pickable)){  //using interface to force pick
                    spreadGems(player,nextStopCell, direction);
                }
                else if ((nextStopCell.isEmpty()) && (board.getNextCellClockwise(nextStopCell).isEmpty())){
                    //switch turn
                    return;
                }
                else if (!(nextStopCell.isEmpty()) && !(nextStopCell instanceof Pickable)){
                    //switch turn
                    return;
                    
                }
                else{
                    while ((nextStopCell.isEmpty()) && !(board.getNextCellClockwise(nextStopCell).isEmpty()) ){
                    Cell earnedCell = board.getNextCellClockwise(nextStopCell);
                    int earnedScore = earnScore(earnedCell);
                    computeScore(player,earnedScore);
                    // System.out.println(earnedScore);
                    earnedCell.setEmpty();
                    Cell copyEarnedCell = earnedCell.copyCell();
                    itinerary.add(copyEarnedCell);

                    nextStopCell = board.getNextCellClockwise(earnedCell);
                    //switch turn
                    }
                    return;
                }
            }

            else if (direction == 0) { //counter clockwise
    
                //spread first round
            
                for (int i = 0; i < numberOfGems; i++) {
                    int index = (locationChosen - i - 1 + totalCell) % totalCell; // Calculate the index correctly
                    Cell nextCell = board.getCells()[index];
                    nextCell.addGem(gemList.get(i));
                    Cell copyNextCell = nextCell.copyCell();
                    itinerary.add(copyNextCell);
                    // System.out.println("location run"+nextCell.getLocation()+ "size"+nextCell.getGemList().size());

                }
                cellChosen.setEmpty();
                Cell copyCellChosen = cellChosen.copyCell();
                int itinerarySize = itinerary.size();
                itinerary.add(itinerarySize-numberOfGems,copyCellChosen);
        

                //check contuinity
                stopCell = board.getCells()[(locationChosen - numberOfGems + totalCell) % totalCell];
                Cell nextStopCell = board.getNextCellCounterClockwise(stopCell);
                if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof Pickable)) {
                    spreadGems(player, nextStopCell, direction);
                    
                } else if ((nextStopCell.isEmpty()) && (board.getNextCellCounterClockwise(nextStopCell).isEmpty())) {
                    //switch turn
                    return;
                } else if (!(nextStopCell.isEmpty()) && !(nextStopCell instanceof Pickable)) {
                    //switch turn
                    return;
                }
                else{
                    while ((nextStopCell.isEmpty()) && !(board.getNextCellCounterClockwise(nextStopCell).isEmpty())) {
                        Cell earnedCell = board.getNextCellCounterClockwise(nextStopCell);
                        if (earnedCell.getGemList().size() > 0){
                            int earnedScore = earnScore(earnedCell);
                            earnedCell.setEmpty();
                            Cell copyEarnedCell = earnedCell.copyCell();
                            itinerary.add(copyEarnedCell);
                            this.computeScore(player, earnedScore);
                            nextStopCell = board.getNextCellCounterClockwise(earnedCell);
                        }
                        return;
                        //switch turn
                    }
                }
            }
        }
    



    public static void main(String[] args){
        Board board = new Board();
        Players player = new Players("player1", "player2", board);
        Cell cell = board.getCells()[9];
        player.spreadGems("player1", cell, 0);
        // System.out.println(player.getScore("player1"));
        // System.out.println(player.getScore("player2"));
        for (Cell cells : player.getItinerary()){
            System.out.println("location" + cells.getLocation() + "size" + cells.getGemList().size());
        }
        // player.setItinerary(new ArrayList<Integer>());
        // Cell cell2 = board.getCells()[8];
        // player.spreadGems("player2", cell2, 0);
        // System.out.println(player.getScore("player1"));
        // System.out.println(player.getScore("player2"));
        System.out.println(player.getItinerary().size());

    }

}
    



    


