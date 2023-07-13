package model.player;

import model.board.Board;

public class Player1 extends Player{
    public Player1(String player1, String player2, Board board) {
        super(player1, player2, board);
        //TODO Auto-generated constructor stub
    }

    private int turn;

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
}
