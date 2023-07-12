package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class PlayController{
    @FXML
    private ImageView btnCCLCell01;

    @FXML
    private ImageView btnCCLCell02;

    @FXML
    private ImageView btnCCLCell03;

    @FXML
    private ImageView btnCCLCell04;

    @FXML
    private ImageView btnCCLCell05;

    @FXML
    private ImageView btnCCLCell07;

    @FXML
    private ImageView btnCCLCell08;

    @FXML
    private ImageView btnCCLCell09;

    @FXML
    private ImageView btnCCLCell10;

    @FXML
    private ImageView btnCCLCell11;

    @FXML
    private ImageView btnCLCell01;

    @FXML
    private ImageView btnCLCell02;

    @FXML
    private ImageView btnCLCell03;

    @FXML
    private ImageView btnCLCell04;

    @FXML
    private ImageView btnCLCell05;

    @FXML
    private ImageView btnCLCell07;

    @FXML
    private ImageView btnCLCell08;

    @FXML
    private ImageView btnCLCell09;

    @FXML
    private ImageView btnCLCell10;

    @FXML
    private ImageView btnCLCell11;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnHome;

    @FXML
    private Pane cell01;

    @FXML
    private Pane cell02;

    @FXML
    private Pane cell03;

    @FXML
    private Pane cell04;

    @FXML
    private Pane cell05;

    @FXML
    private Pane cell07;

    @FXML
    private Pane cell08;

    @FXML
    private Pane cell09;

    @FXML
    private Pane cell10;

    @FXML
    private Pane cell11;

    @FXML
    private Text numGemsCell00;

    @FXML
    private Text numGemsCell01;

    @FXML
    private Text numGemsCell02;

    @FXML
    private Text numGemsCell03;

    @FXML
    private Text numGemsCell04;

    @FXML
    private Text numGemsCell05;

    @FXML
    private Text numGemsCell06;

    @FXML
    private Text numGemsCell07;

    @FXML
    private Text numGemsCell08;

    @FXML
    private Text numGemsCell09;

    @FXML
    private Text numGemsCell10;

    @FXML
    private Text numGemsCell11;

    @FXML
    private Text scorePlayer1;

    @FXML
    private Text scorePlayer2;

    @FXML
    private ImageView turnPlayer1;

    @FXML
    private ImageView turnPlayer2;

    @FXML
    private Button btnHomeWinner;

    @FXML
    private Button btnPlayAgain;


    @FXML
    private ImageView endGameScreen;

    private List<Pane> listPaneOnPlayer1 = new ArrayList<Pane>(); // not exist -> need to declare
    private List<Pane> listPaneOnPlayer2 = new ArrayList<Pane>();
    private Player player;
    private Board board;

    public PlayController(Player player, Board board) {
        this.player = player;
        this.board = board;
    }

    @FXML
    public void initialize() {

        //set btnCL btnCCL invisible
        btnCLCell01.setVisible(false);
        btnCLCell02.setVisible(false);
        btnCLCell03.setVisible(false);
        btnCLCell04.setVisible(false);
        btnCLCell05.setVisible(false);
        btnCLCell07.setVisible(false);
        btnCLCell08.setVisible(false);
        btnCLCell09.setVisible(false);
        btnCLCell10.setVisible(false);
        btnCLCell11.setVisible(false);
        btnCCLCell01.setVisible(false);
        btnCCLCell02.setVisible(false);
        btnCCLCell03.setVisible(false);
        btnCCLCell04.setVisible(false);
        btnCCLCell05.setVisible(false);
        btnCCLCell07.setVisible(false);
        btnCCLCell08.setVisible(false);
        btnCCLCell09.setVisible(false);
        btnCCLCell10.setVisible(false);
        btnCCLCell11.setVisible(false);

        //set 2 frame invisible
        turnPlayer1.setVisible(false);
        turnPlayer2.setVisible(false);

        //add to list of pane
        listPaneOnPlayer1.add(cell01);
        listPaneOnPlayer1.add(cell02);
        listPaneOnPlayer1.add(cell03);
        listPaneOnPlayer1.add(cell04);
        listPaneOnPlayer1.add(cell05);
        listPaneOnPlayer2.add(cell07);
        listPaneOnPlayer2.add(cell08);
        listPaneOnPlayer2.add(cell09);
        listPaneOnPlayer2.add(cell10);
        listPaneOnPlayer2.add(cell11);

        //set end game screen invisible
        btnPlayAgain.setOnAction(event5 ->{
            endGameScreen.setVisible(false);
            board = new Board();
            player = new Player("player1", "player2", board);
            initialize();
        });

        btnHomeWinner.setOnAction(event6 ->{
            // new HomeController();
            // Stage currentStage = (Stage) cell01.getScene().getWindow();
            // currentStage.close();
            endGameScreen.setVisible(false);

        });
        
        endGameScreen.setVisible(false);

        //get cells on player 1
        Cell[] cellsOnPlayer1 = board.getPlayer1Cells(); // may be remove
        for (int i=0; i < cellsOnPlayer1.length; i++) {
            int index = i;
            Pane pane = listPaneOnPlayer1.get(i);
            pane.setOnMouseClicked(event -> {

                if (cellsOnPlayer1[index].getGemList().size() == 0){
                    pane.setDisable(false);
                    System.out.println("Cell could not be clicked");
                    
                }
                else{

                    }

                }

                

            

            });

            
        }

        //set direction then spread gems of player 1


                            

                          
                        }
                        //display number of gems on board 
                        numGemsCell00.setText(Integer.toString(board.getBoard()[0].getGemList().size()));
                        numGemsCell01.setText(Integer.toString(board.getBoard()[1].getGemList().size()));
                        numGemsCell02.setText(Integer.toString(board.getBoard()[2].getGemList().size()));
                        numGemsCell03.setText(Integer.toString(board.getBoard()[3].getGemList().size()));
                        numGemsCell04.setText(Integer.toString(board.getBoard()[4].getGemList().size()));
                        numGemsCell05.setText(Integer.toString(board.getBoard()[5].getGemList().size()));
                        numGemsCell06.setText(Integer.toString(board.getBoard()[6].getGemList().size()));
                        numGemsCell07.setText(Integer.toString(board.getBoard()[7].getGemList().size()));
                        numGemsCell08.setText(Integer.toString(board.getBoard()[8].getGemList().size()));
                        numGemsCell09.setText(Integer.toString(board.getBoard()[9].getGemList().size()));
                        numGemsCell10.setText(Integer.toString(board.getBoard()[10].getGemList().size()));
                        numGemsCell11.setText(Integer.toString(board.getBoard()[11].getGemList().size()));

                        
                        
                        //display score
                        scorePlayer1.setText(Integer.toString(player.getScore("player1")));
                        System.out.println("after clicking direction"+Integer.toString(board.getBoard()[index+1].getGemList().size()));
                        
                        // switch turn
                        switchTurn(pane); // still have error when click to direction it not change turn, 3 time after and also not invisible
                        System.out.println(Integer.toString(player.getTurn()));

                        //consume event avoid to click to parent
                        event1.consume();
                    });


                }
            }
        }

        //get cells on player 2
        Cell[] cellsOnPlayer2 = board.getPlayer2Cells();
        for (int i=0; i < cellsOnPlayer2.length; i++) {
            int index = i;
            Pane pane = listPaneOnPlayer2.get(i);
            pane.setOnMouseClicked(event2 -> {

              
                else{
                    // show direction
                    showDirection(pane);
                    System.out.println("Cell clicked");
                
                    for (int j=0; j < cellsOnPlayer2.length; j++){
                        Pane paneAround = listPaneOnPlayer2.get(j);
                        if (j != index ){
                            paneAround.setDisable(true);
                        }
                        
                    }

                }
            });
            
        }

        //set direction then spread gems of player 2
        for (int i=0; i < cellsOnPlayer2.length; i++){
            int index = i;
            Pane pane = listPaneOnPlayer2.get(index);
            List<Node> children = pane.getChildren();
            for (Node child : children) {
                if (child instanceof ImageView) {
                    ImageView imageView = (ImageView) child;
                    imageView.setOnMouseClicked(event3 -> {
                        System.out.println("Direction clicked");
                        
                        // Set the direction
                        if (imageView.getId().startsWith("btnCCL")) {
                            player.setDirection(0);
                        } else if (imageView.getId().startsWith("btnCL")) {
                            player.setDirection(1);
                        }

                        //spread gems
                        player.spreadGems("player2",board.getBoard()[index+7], player.getDirection());

                        //fake end game
                        board.getBoard()[0].setEmpty();
                        board.getBoard()[6].setEmpty();

                        //check end game
                        if (board.endGame()){
                            System.out.println("end game");
                            player.assembleSmallGems();
                            scorePlayer2.setText(Integer.toString(player.getScore("player2")));
                            scorePlayer1.setText(Integer.toString(player.getScore("player1")));
                            endGameScreen.setVisible(true);

    
                          
                        }

                        //display number of gems
                        numGemsCell00.setText(Integer.toString(board.getBoard()[0].getGemList().size()));
                        numGemsCell01.setText(Integer.toString(board.getBoard()[1].getGemList().size()));
                        numGemsCell02.setText(Integer.toString(board.getBoard()[2].getGemList().size()));
                        numGemsCell03.setText(Integer.toString(board.getBoard()[3].getGemList().size()));
                        numGemsCell04.setText(Integer.toString(board.getBoard()[4].getGemList().size()));
                        numGemsCell05.setText(Integer.toString(board.getBoard()[5].getGemList().size()));
                        numGemsCell06.setText(Integer.toString(board.getBoard()[6].getGemList().size()));
                        numGemsCell07.setText(Integer.toString(board.getBoard()[7].getGemList().size()));
                        numGemsCell08.setText(Integer.toString(board.getBoard()[8].getGemList().size()));
                        numGemsCell09.setText(Integer.toString(board.getBoard()[9].getGemList().size()));
                        numGemsCell10.setText(Integer.toString(board.getBoard()[10].getGemList().size()));
                        numGemsCell11.setText(Integer.toString(board.getBoard()[11].getGemList().size()));

                        

                        //set score
                        scorePlayer2.setText(Integer.toString(player.getScore("player2")));
                        System.out.println("after clicking direction" + Integer.toString(board.getBoard()[index+7].getGemList().size()));

                        

                        // switch turn
                        switchTurn(pane);
                        System.out.println(Integer.toString(player.getTurn()));
                        event3.consume();
                    });
                }
            }
        }

        // run
        Random rand = new Random();
        int randTurn = rand.nextInt(2) + 1;
        player.setTurn(randTurn);
        if (player.getTurn() == 1){
            turnPlayer1.setVisible(true);
            turnPlayer2.setVisible(false);
            //set able for cells 1
            for (int i=0; i < board.getNumSquares()/2; i++) {
                Pane pane = listPaneOnPlayer1.get(i);
                pane.setDisable(false);
            }

            //set disable for cells 2
            for (int i=0; i < board.getNumSquares()/2; i++) {
                Pane pane = listPaneOnPlayer2.get(i);
                pane.setDisable(true);
            }
        }
        else{
            turnPlayer1.setVisible(false);
            turnPlayer2.setVisible(true);
            //set disable for cells 1
            for (int i=0; i < board.getNumSquares()/2; i++) {
                Pane pane = listPaneOnPlayer1.get(i);
                pane.setDisable(true);
            }

            //set able for cells 2
            for (int i=0; i < board.getNumSquares()/2; i++) {
                Pane pane = listPaneOnPlayer2.get(i);
                pane.setDisable(false);
            }
        }

    }

    public void switchTurn(Pane paneChosen){

        List<Node> children = paneChosen.getChildren();

        // Set both direction buttons in the pane to invisible
        for (Node child : children) {
            if (child instanceof ImageView) {
                child.setVisible(false);
            }
        }

        if (player.getTurn() == 1){

            // display turn
            turnPlayer1.setVisible(false);
            turnPlayer2.setVisible(true);
            
            //set able for cells 1
            for (int i=0; i < board.getNumSquares()/2; i++) {
                Pane pane = listPaneOnPlayer1.get(i);
                pane.setDisable(true);
            }

            //set disable for cells 2
            for (int i=0; i < board.getNumSquares()/2; i++) {
                Pane pane = listPaneOnPlayer2.get(i);
                pane.setDisable(false);
            }

            player.setTurn(2);
        
        }else{

            // display turn
            turnPlayer1.setVisible(true);
            turnPlayer2.setVisible(false);
            
            //set disable for cells 1
            for (int i=0; i < board.getNumSquares()/2; i++) {
                Pane pane = listPaneOnPlayer1.get(i);
                pane.setDisable(false);
            }

            //set able for cells 2
            for (int i=0; i < board.getNumSquares()/2; i++) {
                Pane pane = listPaneOnPlayer2.get(i);
                pane.setDisable(true);
            }
            player.setTurn(1);
            }
        }
    
    public void showDirection(Pane pane) {
        // Retrieve the children of the Pane
        List<Node> children = pane.getChildren();
        // Loop through the children of the Pane
        for (Node child : children) {
            // Check if the child is an ImageView
            if (child instanceof ImageView) {
                ImageView imageView = (ImageView) child;
                // Set the visibility of the ImageView to true
                imageView.setVisible(true); 
            }
        }
    }

}

