package model.player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.board.Board;
import model.board.Cell;
import model.board.Pickable;
import model.gem.*;

public class Competitors { //set action of players with board. Do not make for each player, we set for all players -> Easy to keep track turn. Do not use inheritance here because children are actually instances of the same class
    private Player player1;
    private Player player2;
    private int turn;
    private int direction;
    private Board board;
    private LinkedHashMap<Integer, Integer> creditHistory = new LinkedHashMap<Integer, Integer>(); // (1,5) -> player 1 borrow 5 small gems form player 2. need to reset at new game
    private List<Cell> itinerary = new ArrayList<Cell>(); 
    
    public Competitors(Player player1, Player player2, Board board){ // need to construct player vs board at first
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

    public Player getPlayer1(){
        return player1;
    }

    public Player getPlayer2(){
        return player2;
    }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public int getTurn(){
        return turn;
    }
    
    public void setTurn(int turn){
        this.turn = turn;
    }
    public Board getBoard(){
        return board;
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
    

    public void reduceScore(Player player){  //default 5 gems borrowed
        reduceScore(player, 5);
    }


    public void reduceScore(Player player, int score){
        if (player.equals(player1)){
            player1.setScore(player1.getScore()-score);
            for (int i = 0; i < score; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+1].addGem(smallGem);
            }
        } 
        else if (player.equals(player2)){
            player2.setScore(player2.getScore()-score);
            for (int i = 0; i < score; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+board.getNumSquares()/2+2].addGem(smallGem);
            }
        }
    }

    public String getWinner(){
        if (player1.getScore() > player2.getScore()){
            return "1";
        }
        else if (player1.getScore() < player2.getScore()){
           return "2";
        }
        else{
            return "draw";
        }
    }

    public void borrow(Player playerOne, Player playerTwo){ // 1 borrow 2
        if (player1.equals(playerOne) && player2.equals(playerTwo)){
            player2.setScore(player2.getScore()- 5);
            creditHistory.put(1, 5);
            for (int i = 0; i < 5; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+1].addGem(smallGem);
            }
        } 
        else if (player1.equals(playerTwo) && player2.equals(playerOne)){
            player1.setScore(player2.getScore()-5);
            creditHistory.put(2, 5);
            for (int i = 0; i < 5; i++){
                Gem smallGem = new SmallGem();
                board.getCells()[i+board.getNumSquares()/2+2].addGem(smallGem);
            }
        }
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

            if (!cell.isEmpty()){
                int earnedScore = earnScore(cell);
                cell.setEmpty();
                if (i<6){
                    player1.computeScore(earnedScore);
                }
                else if (i>6){
                    player2.computeScore(earnedScore);
                }
                //save itinerary
                Cell copyCell = cell.copyCell();
                itinerary.add(copyCell);
            }
        }   
    }

        // return borrowed gems
        for (Map.Entry<Integer, Integer> entry : creditHistory.entrySet()) {
            if (entry.getKey() == 1){
                player2.computeScore(entry.getValue());
                this.reduceScore(player1); // always maximum 5. defalt 5
            }
            else if (entry.getKey() == 2){
                player1.computeScore(entry.getValue());
                this.reduceScore(player2); // always maximum 5. defalt 5
            }
        }
    }
    
    public void spreadGems(Player player, int locationChosen, int direction){
            Cell stopCell;
            Cell cellChosen = board.getCells()[locationChosen].copyCell();
            List<Gem> gemList = cellChosen.getGemList(); //pass by value
            int numberOfGems = gemList.size();
            int totalCell = board.getCells().length;

            board.getCells()[locationChosen].setEmpty();
            Cell copyCellChosen = board.getCells()[locationChosen].copyCell();
            itinerary.add(copyCellChosen);

            if (direction == 1){ //clockwise

                //spread first round
                for (int i = 0; i < numberOfGems; i++) {
                    int index = (locationChosen + i + 1) % totalCell; 
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
                    if (player.equals(player1)){
                        player1.computeScore(earnedScore);
                    }
                    else if (player2.equals(player2)){
                        player2.computeScore(earnedScore);
                    }
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
                    // System.out.println(this.getPlayer1() + " 2 empty" + this.getScore("player1") + " " + "player2" + " " + this.getScore("player2"));

                    return;
                } else if (!(nextStopCell.isEmpty()) && !(nextStopCell instanceof Pickable)) {
                    //switch turn
                    // System.out.println(this.getPlayer1() + " halfcircle" + this.getScore("player1") + " " + "player2" + " " + this.getScore("player2"));

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
                            System.out.println("earned" + earnedCell.getLocation() + "size" + earnedCell.getGemList().size());
                            if (player.equals(player1)) {
                                player.computeScore(earnedScore);
                            } else if (player.equals(player2)) {
                                player.computeScore(earnedScore);
                            }
                            // System.out.println(this.getPlayer1() + " while" + this.getScore("player1") + " " + "player2" + " " + this.getScore("player2"));
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

    public boolean checkNoGemsOnSide(Player player){ //if on player side, each square does not have any gems return true
        for (int i = 1; i < board.getCells().length; i++){
            if (player.equals(player1)){
                if (i<6 && board.getCells()[i].getGemList().size() > 0){
                    return false;
                }
            }
            else if (player.equals(player2)){
                if (i>6 && board.getCells()[i+board.getNumSquares()/2+1].getGemList().size() > 0){
                    return false;
                }
            }
        }
        return true;

    }


    public static void main(String[] args){
        Board board = new Board();
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Competitors player = new Competitors(player1,player2, board);
        player.spreadGems( player.getPlayer2(),9, 1);
        for (Cell cells : player.getItinerary()){
            System.out.println("location" + cells.getLocation() + "size" + cells.getGemList().size());
        }
        
        System.out.println(player.getItinerary().size());

    }

}
    
