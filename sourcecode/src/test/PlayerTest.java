
import java.util.ArrayList;

import model.board.Board;
import model.board.Cell;
import model.player.Player;

public class PlayerTest {
    public static void main(String[] args) {
        Board board = new Board();
        Cell[] boardList = board.getBoard();
        Player player = new Player("Thanh","Thiem", board);  // plays in the upper part

        ArrayList<Cell> cellOnSide = new ArrayList<>();


        for(int i=1; i < 6; i++) {
            cellOnSide.add(boardList[i]);
        }
        player.getCellChosen();

        System.out.println("cells on side of player: " + player.getCellChosen() );

        // initial states
        System.out.println("Initial board: ");
        for(Cell cell:boardList) {
            System.out.println(cell.seeDetails());
        }
        System.out.println("Initial player turn: " + player.getTurn());
        System.out.println();

        // test spread gem
        System.out.println();
        System.out.println("Test spread");
        System.out.println(player.getScore("Thanh"));
        player.spreadGems("Thanh",boardList[2],1); // anti-clockwise
        System.out.println(player.getScore("Thanh"));
        for(Cell cell:boardList) {
            System.out.println(cell.seeDetails());
        }
     }
}