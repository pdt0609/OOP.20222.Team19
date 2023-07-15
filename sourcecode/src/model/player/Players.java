package model.player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    // private static Board board;
    private Board board;
    private static LinkedHashMap<Integer, Integer> creditHistory = new LinkedHashMap<Integer, Integer>(); // (1,5) -> player 1 borrow 5 small gems form player 2. need to reset at new game
    private List<Cell> itinerary = new ArrayList<Cell>(); 
    
    public Players(String player1, String player2, Board board){ // need to construct player vs board at first
        this.player1 = player1;
        this.player2 = player2;
        // Players.board = board;
        this.board =board;
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

    public void reduceScore(String player){  //default 5 gems borrowed
        reduceScore(player, 5);
    }

    public void reduceScore(String player, int score){
        if (player.equals(this.getPlayer1())){
            this.score1 = this.score1 - score;
            for (int i = 0; i< score; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+1].addGem(smallGem);
            }
        } 
        else if (player.equals(this.getPlayer2())){
            this.score2 = this.score2 - score;
            for (int i = 0; i< score; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+board.getNumSquares()/2+2].addGem(smallGem);
            }
        }
    }

    public void borrow(String player1, String player2){ // 1 borrow 2
        if (player1.equals(this.getPlayer1()) && player2.equals(this.getPlayer2())){
            this.score2 = this.score2 - 5;
            creditHistory.put(1, 5);
            for (int i = 0; i < 5; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+1].addGem(smallGem);
            }
        } 
        else if (player1.equals(this.getPlayer2()) && player2.equals(this.getPlayer1())){
            this.score1 = this.score1 - 5;
            creditHistory.put(2, 5);
            for (int i = 0; i < 5; i++){
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
    public void assembleSmallGems(List<Cell> itinerary){  // it takes gems on side and pay gems debt automatically

    for (int i = 0; i < board.getNumSquares() + board.getNumHalfCircles(); i++){

        if (i !=0 && i!=6){
            Cell cell = board.getCells()[i];
            int earnedScore = earnScore(cell);
            cell.setEmpty();

            if (i<6){
                this.computeScore(this.getPlayer1(), earnedScore);
            }
            else if (i>6){
                this.computeScore(this.getPlayer2(), earnedScore);
            }
            //save itinerary
            Cell copyCell = cell.copyCell();
            copyCell.setEarnedCell();
            itinerary.add(copyCell);
            }
        }

        // return borrowed gems
        for (Map.Entry<Integer, Integer> entry : creditHistory.entrySet()) {
            if (entry.getKey() == 1){
                this.computeScore(player2, entry.getValue());
                this.reduceScore(player1); // always maximum 5. defalt 5
            }
            else if (entry.getKey() == 2){
                this.computeScore(player1, entry.getValue());
                this.reduceScore(player2); // always maximum 5. defalt 5
            }
        }
    }
    
    public void spreadGems(String player, int locationChosen, int direction){
            Cell stopCell;
            Cell cellChosen = board.getCells()[locationChosen].copyCell();
            List<Gem> gemList = cellChosen.getGemList(); //pass by reference
            int numberOfGems = gemList.size();
            int totalCell = board.getCells().length;

            board.getCells()[locationChosen].setEmpty();
            Cell copyCellChosen = board.getCells()[locationChosen].copyCell();
            itinerary.add(copyCellChosen);

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
                    board.getCells()[index].addGem(gemList.get(i));
                    Cell copyNextCell = board.getCells()[index].copyCell();
                    itinerary.add(copyNextCell);
                }
               

                

                //check contuinity
                stopCell = board.getCells()[(locationChosen + numberOfGems) % totalCell];
                Cell nextStopCell = board.getNextCellClockwise(stopCell);

                if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof Pickable)){  //using interface to force pick
                    int nextLocation = nextStopCell.getLocation();
                
                    spreadGems(player,nextLocation, direction);
                    
                    
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
                    copyEarnedCell.setEarnedCell();
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
                    board.getCells()[index].addGem(gemList.get(i));
                    Cell copyNextCell = board.getCells()[index].copyCell();
                    itinerary.add(copyNextCell);

                }

                //check contuinity
                stopCell = board.getCells()[(locationChosen - numberOfGems + totalCell) % totalCell];
                Cell nextStopCell = board.getNextCellCounterClockwise(stopCell);
                if (!(nextStopCell.isEmpty()) && (nextStopCell instanceof Pickable)) {
                    int nextLocation = nextStopCell.getLocation();
                    spreadGems(player, nextLocation, direction);
                    
                } else if ((nextStopCell.isEmpty()) && (board.getNextCellCounterClockwise(nextStopCell).isEmpty())) {
                    //switch turn
                    System.out.println(this.getPlayer1() + " 2 empty" + this.getScore("player1") + " " + "player2" + " " + this.getScore("player2"));

                    return;
                } else if (!(nextStopCell.isEmpty()) && !(nextStopCell instanceof Pickable)) {
                    //switch turn
                    System.out.println(this.getPlayer1() + " halfcircle" + this.getScore("player1") + " " + "player2" + " " + this.getScore("player2"));

                    return;
                }
                else{
                    while ((nextStopCell.isEmpty()) && !(board.getNextCellCounterClockwise(nextStopCell).isEmpty())) {
                        Cell earnedCell = board.getNextCellCounterClockwise(nextStopCell);
                        if (earnedCell.getGemList().size() > 0){
                            int earnedScore = earnScore(earnedCell);
                            earnedCell.setEmpty();
                            Cell copyEarnedCell = earnedCell.copyCell();
                            copyEarnedCell.setEarnedCell();
                            itinerary.add(copyEarnedCell);
                            System.out.println("earned" + earnedCell.getLocation() + "size" + earnedCell.getGemList().size());
                            this.computeScore(player, earnedScore);
                            System.out.println(this.getPlayer1() + " while" + this.getScore("player1") + " " + "player2" + " " + this.getScore("player2"));
                            nextStopCell = board.getNextCellCounterClockwise(earnedCell);
                        }
                        return;
                        //switch turn
                    }
                }
            }
        }

    public void resetCreditHistory(){
        creditHistory = new LinkedHashMap<Integer, Integer>();
    }

    public boolean checkNoGemsOnSide(String player){ //if on player side, each square does not have any gems return true
        if (player.equals(this.getPlayer1())){
            for (Cell cell : board.getPlayer1Cells()){
                if (cell.getGemList().size() > 0){
                    return false;
                }
            }
        }
        else if (player.equals(this.getPlayer2())){
            for (Cell cell : board.getPlayer2Cells()){
                if (cell.getGemList().size() > 0){
                    return false;
                }
            }
        }

        return true;

    }
    public int getPreviousScore(String player, List<Cell> itinerary){
        int previousScore = this.getScore(player);
        for (Cell cell : itinerary){
            if (cell.isEarnedCell()){
                previousScore = previousScore - earnScore(cell);
            }
        }
        return previousScore;
    }
    



    public static void main(String[] args){
        Board board = new Board();
        Players player = new Players("player1", "player2", board);
        Cell cell = board.getCells()[9];
        player.spreadGems("player2",9, 0);
        // System.out.println(player.getScore("player1"));
        // System.out.println(player.getScore("player2"));
        for (Cell cells : player.getItinerary()){
            if (cells.isEarnedCell()){
                System.out.println("earned"+ cells.getLocation() + "size" + cells.getGemList().size());
            }
            // System.out.println("location" + cells.getLocation() + "size" + cells.getGemList().size());
        }
        // player.setItinerary(new ArrayList<Integer>());
        // Cell cell2 = board.getCells()[8];
        // player.spreadGems("player2", cell2, 0);
        // System.out.println(player.getScore("player1"));
        // System.out.println(player.getScore("player2"));
        System.out.println(player.getItinerary().size());
        System.out.println(player.getPlayer1() + " " + player.getScore("player1") + " " + "player2" + " " + player.getScore("player2"));


    }

}
    

