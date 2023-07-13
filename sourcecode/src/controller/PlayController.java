package controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.board.Board;
import model.player.Players;

public class PlayController{

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnHome;

    @FXML
    private Pane cell00;

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
    private Pane cell06;

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
    private AnchorPane endGameScreen;

    @FXML
    private Text winnerName;

    @FXML
    private Text winnerScore;

    @FXML
    private String player1Name;

    @FXML
    private String player2Name;

    @FXML
    private Text name1Display;

    @FXML
    private Text name2Display;



    private List<Pane> paneList; // not exist -> need to declare
    private Players players;
    private Board board;
    int numberOfCells;

    public PlayController(Players players, Board board) {
        this.players = players;
        this.board = board;
        this.numberOfCells = board.getNumSquares() +board.getNumHalfCircles();
        this.player1Name = players.getPlayer1();
        this.player2Name = players.getPlayer2();
    }

    @FXML
    void btnAccessHelpClicked(ActionEvent event) {

            Stage currentStage = (Stage) btnHome.getScene().getWindow();
            currentStage.close();
        try {
            final String HELP_SCREEN_FILE_PATH = "/view/HelpScreen.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(HELP_SCREEN_FILE_PATH));
            fxmlLoader.setController(new HelpScreenController());
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // set new scene for current stage
            stage.setScene(new Scene(root));
            stage.setTitle("Help Screen");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @FXML
    public void btnBackFromHomeControllerClicked(ActionEvent event) {
        Stage currentStage = (Stage) btnHome.getScene().getWindow();
        currentStage.close();
        try {
            // TODO while playing: just a pop-up

            final String INTRO_SCREEN_FILE_PATH = "/view/Home.fxml";

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(INTRO_SCREEN_FILE_PATH));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Intro Screen");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        //set 2 frame invisible
        turnPlayer1.setVisible(false);
        turnPlayer2.setVisible(false);
        paneList = Arrays.asList(cell00, cell01, cell02, cell03, cell04,cell05,cell06,cell07,cell08,cell09,cell10,cell11);

        //hide direction initially
        for (int i=0; i < numberOfCells; i++){
            Pane pane = paneList.get(i);
            List<Node> children = pane.getChildren();
            for (Node child : children) {
                if (child instanceof ImageView) {
                    ImageView imageView = (ImageView) child;
                    imageView.setVisible(false);
                }
            }
        }

        //set play again button
        btnPlayAgain.setOnAction(event ->{
            endGameScreen.setVisible(false);
            //reset board
            board = new Board();
            players = new Players(player1Name,player2Name, board);
            initialize();
            //display
            this.setDisplay();
        });

        //set home button
        btnHomeWinner.setOnAction(event ->{
            endGameScreen.setVisible(false);
            Stage currentStage = (Stage) btnHomeWinner.getScene().getWindow();
            currentStage.close();
            // player.resetScore();
            
            try {

            final String INTRO_SCREEN_FILE_PATH = "/view/Home.fxml";

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(INTRO_SCREEN_FILE_PATH));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Intro Screen");

            } catch (IOException e) {
                e.printStackTrace();
            }
            
            });
        endGameScreen.setVisible(false);

        //set cell clickable and set cell around is disable
        
        for (int i=0; i < numberOfCells; i++) {
            int index = i;
            if ((i != 0) && (i != 6)){
                Pane pane = paneList.get(i);
                pane.setOnMouseClicked(event -> {

                    if (board.getCells()[index].getGemList().size() == 0){
                        pane.setDisable(false);
                        System.out.println("Cell could not be clicked");
                        if (players.getTurn() == 1){
                            if (board.checkNoGemsOnSide(player1Name)){
                            players.reduceScore(player1Name);
                            this.setDisplay();
                            }

                        }
                        else if (players.getTurn() == 2){
                            if (board.checkNoGemsOnSide(player2Name)){
                            players.reduceScore(player2Name);
                            this.setDisplay();
                            }
                        }
                    }
                    else{
                        System.out.println("Cell clicked");
                        
                        //show direction
                        showDirection(pane);
                        
                        for (int j=0; j < numberOfCells ; j++){
                            if (j != index ){
                                Pane paneAround = paneList.get(j);
                                paneAround.setDisable(true);
                            }
                        }
                    }
                });
            }
        }

        //set direction then spread gems of player 1
        for (int i=0; i < numberOfCells; i++){
            int index = i;
            if ((i != 0) && (i != 6)){
                Pane pane = paneList.get(index);
                List<Node> children = pane.getChildren();
                for (Node child : children) {
                    if (child instanceof ImageView) {
                        ImageView imageView = (ImageView) child;

                        imageView.setOnMouseClicked(event1 -> {
                            System.out.println("Direction clicked");

                            // Set the direction
                            if (imageView.getId().startsWith("btnCCL")) {
                                players.setDirection(0);
                            } else if (imageView.getId().startsWith("btnCL")) {
                                players.setDirection(1);
                            }

                            //spread gems
                            if (players.getTurn() == 1){
                                players.spreadGems(player1Name,board.getCells()[index], players.getDirection());
                            }
                            else if(players.getTurn() == 2){
                                players.spreadGems(player2Name,board.getCells()[index], players.getDirection());
                            }

                            //fake end game
                            // board.getCells()[0].setEmpty();
                            // board.getCells()[6].setEmpty();

                            //check end game
                            if (board.endGame()){
                                System.out.println("end game");
                                players.assembleSmallGems();
                                if (players.getScore(player1Name) > players.getScore(player2Name)){
                                    winnerName.setText("1");
                                    winnerScore.setText(Integer.toString(players.getScore(player1Name)));
                                }
                                else if (players.getScore(player1Name) < players.getScore(player2Name)){
                                    winnerName.setText("2");
                                    winnerScore.setText(Integer.toString(players.getScore(player2Name)));
                                }
                                else{
                                    winnerName.setText("Draw");
                                    winnerScore.setText(Integer.toString(players.getScore(player1Name)));
                                }

                                endGameScreen.setVisible(true);

                            }

                            //display number of gems
                            this.setDisplay();

                            switchTurn(pane); // still have error when click to direction it not change turn, 3 time after and also not invisible
                            System.out.println(Integer.toString(players.getTurn()));
                            event1.consume();
                        });


                    }
                }
            }
        }

        // Initialize play
        Random rand = new Random();
        int randTurn = rand.nextInt(2) + 1;
        players.setTurn(randTurn);
        if (players.getTurn() == 1){
            turnPlayer1.setVisible(true);
            turnPlayer2.setVisible(false);
            //set able for cells 1
            for (int i=0; i < numberOfCells; i++) {
                if ((i != 0) && (i != 6)){
                    Pane pane = paneList.get(i);
                    if (i < 6)
                        pane.setDisable(false);
                    else
                        pane.setDisable(true);
                }
            }
            
            

        }else{
            turnPlayer1.setVisible(false);
            turnPlayer2.setVisible(true);
            //set disable for cells 1
            for (int i=0; i < numberOfCells; i++) {
                if ((i != 0) && (i != 6)){
                    Pane pane = paneList.get(i);
                    if (i < 6)
                        pane.setDisable(true);
                    else
                        pane.setDisable(false);
                }
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

        if (players.getTurn() == 1){

            // display turn
            turnPlayer1.setVisible(false);
            turnPlayer2.setVisible(true);

            for (int i=0; i < numberOfCells; i++) {
                if ((i != 0) && (i != 6)){
                    Pane pane = paneList.get(i);
                    if (i < 6)
                        pane.setDisable(true);
                    else
                        pane.setDisable(false);
                }
            }
            //set new turn
            players.setTurn(2);
        
        }else{

            // display turn
            turnPlayer1.setVisible(true);
            turnPlayer2.setVisible(false);
            
            for (int i=0; i < numberOfCells; i++) {
                if ((i != 0) && (i != 6)){
                    Pane pane = paneList.get(i);
                    if (i < 6)
                        pane.setDisable(false);
                    else
                        pane.setDisable(true);
                }
            }
            //set new turn
            players.setTurn(1);
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

    public void setDisplay(){
        for (int i=0; i < board.getCells().length; i++){
            Pane pane = paneList.get(i);
            Text numberOfGems = (Text) pane.getChildren().get(0); // downcast
            numberOfGems.setText(Integer.toString(board.getCells()[i].getGemList().size()));

        }
        scorePlayer2.setText(Integer.toString(players.getScore("player2")));
        scorePlayer1.setText(Integer.toString(players.getScore("player1")));
    }

}

