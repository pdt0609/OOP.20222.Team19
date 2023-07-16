package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.board.Board;
import model.board.Cell;
import model.player.Player;
import myexception.AllEmptyCellOnSideException;
import myexception.EmptyCellException;
import myexception.WrongCellException;
import model.player.Competitors;


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
    private Text name1Display;

    @FXML
    private Text name2Display;

    @FXML
    private Text winnerScore1;

    @FXML
    private Text winnerScore2;

    @FXML
    private Text player1Winner;

    @FXML
    private Text player2Winner;

    @FXML
    private AnchorPane helpScreen;

    @FXML
    private Button btnContinue;

    @FXML
    private Text name1;

    @FXML
    private Text name2;

    @FXML
    private Button btnExit;

    @FXML 
    private Button btnReplay;

    private Timeline timeline = new Timeline() ;
    private List<Pane> paneList;
    private Competitors players;
    private Board board;
    int numberOfCells;

    public PlayController(Competitors players) { // just use players to access data
        this.players = players;
        this.board = players.getBoard();
        this.numberOfCells = board.getNumSquares() +board.getNumHalfCircles();
    }

    @FXML
    void btnExitGameClicked(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Exit Game");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> res = alert.showAndWait();
        if (res.get() == ButtonType.OK) {
            // quit game
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        } else {
            // close dialog
            alert.close();
        }
    }

    

    @FXML
    public void initialize() {

        //SET DISPLAY
        paneList = Arrays.asList(cell00, cell01, cell02, cell03, cell04,cell05,cell06,cell07,cell08,cell09,cell10,cell11);
        helpScreen.setVisible(false);
        name1.setText(players.getPlayer1().getName());
        name2.setText(players.getPlayer2().getName());
        endGameScreen.setVisible(false);
        turnPlayer1.setVisible(false);
        turnPlayer2.setVisible(false);
        this.setDisplay(board);

        //SET ACTION
        btnHelp.setOnAction(event -> {
            helpScreen.setVisible(true);
        });

        btnContinue.setOnAction(event -> {
            helpScreen.setVisible(false);
        });

        btnHome.setOnAction(event -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("O An Quan Home Screen");
                stage.show();
        
                // Close the current stage (optional)
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnReplay.setOnAction(event -> {
            board = new Board();
            Player newPlayer1 = new Player(players.getPlayer1().getName());
            Player newPlayer2 = new Player(players.getPlayer2().getName());

            players = new Competitors(newPlayer1,newPlayer2, board);
            initialize();
            //display
            this.setDisplay(board);
        });
        
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
            Player newPlayer1 = new Player(players.getPlayer1().getName());
            Player newPlayer2 = new Player(players.getPlayer2().getName());

            players = new Competitors(newPlayer1,newPlayer2, board);
            initialize();
            //display
            this.setDisplay(board);
        });

        //set home button
        btnHomeWinner.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/view/images/icon.png")));
                stage.setScene(new Scene(root));
                stage.setTitle("Home Screen");
                stage.show();
        
                // Close the current stage (optional)
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        for (int i = 0; i < numberOfCells; i++) {
            int index = i;

            if (i != 0 && i != 6) {
                Pane pane = paneList.get(i);
                pane.setOnMouseClicked(event -> {
                    try {
                        handleCellClick(pane, index);
                    } catch (EmptyCellException e) {
                        // Show an alert with the custom exception message
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Empty Cell");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    } catch (AllEmptyCellOnSideException e) {
                        // Show an alert with the custom exception message
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("All Empty Cells on Side");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                        if (players.getTurn() == 1){
                            if (players.checkNoGemsOnSide(players.getPlayer1())){
                                if (players.getPlayer1().getScore() >= 5){
                                    players.reduceScore(players.getPlayer1());
                                    this.setDisplay(board);
                                }
                                else if (players.getPlayer1().getScore() < 5 && players.getPlayer2().getScore() >= 5){
                                    players.borrow(players.getPlayer1(), players.getPlayer2());
                                    this.setDisplay(board);
                                }
                                // do not consider if both players have score < 5, it is impossible
                            }

                        }else if (players.getTurn() == 2){
                            if (players.checkNoGemsOnSide(players.getPlayer2())){
                                if (players.getPlayer2().getScore() >= 5){
                                    players.reduceScore(players.getPlayer2());
                                    this.setDisplay(board);
                                }
                                else if (players.getPlayer2().getScore() < 5 && players.getPlayer1().getScore() >= 5){
                                    players.borrow(players.getPlayer2(), players.getPlayer1());
                                    this.setDisplay(board);
                                }
                                // do not consider if both players have score < 5, it is impossible
                            }
                        }
                    }
                });
            }
        }

        //set direction then spread gems of player 1
        for (int i =0; i < numberOfCells; i++){
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

                                players.spreadGems(players.getPlayer1(),index, players.getDirection());

                            }
                            else if (players.getTurn() == 2){
                                players.spreadGems(players.getPlayer2(),index, players.getDirection());
                            }
                          

                            // fake end game
                            // board.getCells()[0].setEmpty();
                            // board.getCells()[6].setEmpty();

                            // check end game (inside)
                            if (board.endGame()){
                                System.out.println("end game");
                                players.assembleSmallGems(players.getItinerary());
                                winnerName.setText(players.getWinner());
                                winnerScore1.setText(Integer.toString(players.getPlayer1().getScore()));
                                winnerScore2.setText(Integer.toString(players.getPlayer2().getScore()));

                            }

                            //display number of gems
                            this.setMotionDisplay(players.getItinerary(), pane);

                            players.setItinerary(new ArrayList<Cell>());
 
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
            // set able for cells 1
            for (int i=0; i < numberOfCells; i++) {
                if ((i != 0) && (i != 6)){
                    Pane pane = paneList.get(i);
                    if (i < 6){
                        pane.setDisable(false);
                    }
                    else{
                        //set opacity for text
                        paneList.get(i).setDisable(true);
                    }
                        
                }
            }
            
            
        }else{
            turnPlayer1.setVisible(false);
            turnPlayer2.setVisible(true);
            // set disable for cells 1
            for (int i=0; i < numberOfCells; i++) {
                if ((i != 0) && (i != 6)){
                    Pane pane = paneList.get(i);
                    if (i < 6){
                        pane.setDisable(true);
                    }
                    else
                        pane.setDisable(false);
                }
            }
        }
    }

    private void handleCellClick(Pane pane, int index) throws EmptyCellException, AllEmptyCellOnSideException{
        if (board.getCells()[index].getGemList().size() == 0) {
            if (players.getTurn() == 1) {
                if (players.checkNoGemsOnSide(players.getPlayer1())) {
                    throw new AllEmptyCellOnSideException("Please take earned gems and put them into the empty cells on your side!\nTo do that, click OK then choose cell on your side, gems will be spread automatically!");
                }
            } else if (players.getTurn() == 2) {
                if (players.checkNoGemsOnSide(players.getPlayer2())) {
                    throw new AllEmptyCellOnSideException("Please take earned gems and put them into the empty cells on your side!\nTo do that, click OK then choose cell on your side, gems will be spread automatically!");
                }
            }
            throw new EmptyCellException("Please click on a non-empty cell!");
        } else {
            System.out.println("Cell clicked");

            // Show direction
            showDirection(pane);
            for (int j = 0; j < paneList.size(); j++) {
                if (j != index) {
                    Pane paneAround = paneList.get(j);
                    paneAround.setDisable(true);
                }
            }
        }
    }

    public void switchTurn(Pane paneChosen){
    
        if (players.getTurn() == 1){
            // display turn
            turnPlayer1.setVisible(false);
            turnPlayer2.setVisible(true);

            for (int i=0; i < numberOfCells; i++) {
                if ((i != 0) && (i != 6)){
                    Pane pane = paneList.get(i);
                    if (i < 6){
                        pane.setDisable(true);
                        
                    }
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
                    if (i < 6){
                        pane.setDisable(false);
                    }
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

    public void setDisplay(Board board){
        for (int i=0; i < board.getCells().length; i++){
            Pane pane = paneList.get(i);
            for (Node child : pane.getChildren()) {
                if (child instanceof Text) {
                    Text text = (Text) child;
                    if (child.getId().startsWith("numGems")) {
                        text.setText(Integer.toString(board.getCells()[i].getGemList().size()));
                    }if (child.getId().startsWith("small")) {
                        text.setText("*".repeat(board.getCells()[i].getNumberOfSmallGems()));
                    }
                    if (child.getId().startsWith("big")) {
                        text.setText("*".repeat(board.getCells()[i].getNumberOfBigGems()));
                    }
                    

                         
                }
            }

        }
        scorePlayer2.setText(Integer.toString(players.getPlayer1().getScore()));
        scorePlayer1.setText(Integer.toString(players.getPlayer2().getScore()));
    }


    public void setMotionDisplay(List<Cell> itinerary, Pane paneChosen) {
    
        List<Node> children = paneChosen.getChildren();
        // Set both direction buttons in the pane to invisible after clicking
        for (Node child : children) {
            if (child instanceof ImageView) {
                child.setVisible(false);
            }
        }

        int longDisplay = itinerary.size() ;
        if (!itinerary.isEmpty()) { // Add a check to avoid accessing an empty list

            timeline.getKeyFrames().clear();
            for (int i = 0; i < longDisplay ; i++){
                int index = i;
                Cell cell = itinerary.get(index);
                int id = cell.getLocation();
                Pane pane = paneList.get(id);
                timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(i*0.5), event -> { // time to display
                if (!board.endGame()){
                        
                    for (Node child : pane.getChildren()) {
                        if (child instanceof Text) {
                            Text text = (Text) child; //downcasting
                            if (child.getId().startsWith("numGems")) {
                                text.setText(Integer.toString(cell.getGemList().size()));
                            }if (child.getId().startsWith("small")) {
                                text.setText("*".repeat(cell.getNumberOfSmallGems()));
                            }
                            if (child.getId().startsWith("big")) {
                                text.setText("*".repeat(cell.getNumberOfBigGems()));
                            }
                        }
                    }

                    System.out.println("location "+ itinerary.get(index).getLocation() + " " + itinerary.get(index).getGemList().size() + " " + cell.getNumberOfSmallGems() + " " + cell.getNumberOfBigGems());

                    if (index == longDisplay-1){
                        scorePlayer2.setText(Integer.toString(players.getPlayer1().getScore()));
                        scorePlayer1.setText(Integer.toString(players.getPlayer2().getScore()));
                        switchTurn(paneChosen);
                    } 
                }

                if (board.endGame()){ // it actually always true because results were computed before
    
                    for (Node child : pane.getChildren()) {
                        if (child instanceof Text) {
                            Text text = (Text) child; //downcasting
                            if (child.getId().startsWith("numGems")) {
                                text.setText(Integer.toString(cell.getGemList().size()));
                            }if (child.getId().startsWith("small")) {
                                text.setText("*".repeat(cell.getNumberOfSmallGems()));
                            }if (child.getId().startsWith("big")) {
                                text.setText("*".repeat(cell.getNumberOfBigGems()));
                            }
                        }
                    }
                    System.out.println("location "+ itinerary.get(index).getLocation() + " " + itinerary.get(index).getGemList().size() + " " + cell.getNumberOfSmallGems() + " " + cell.getNumberOfBigGems());

                    if (index == longDisplay - 1){
                        players.resetCreditHistory();
                        endGameScreen.setVisible(true); //auto display borrow score, assemble score
                        scorePlayer2.setText(Integer.toString(players.getPlayer1().getScore()));
                        scorePlayer1.setText(Integer.toString(players.getPlayer2().getScore()));
                        winnerScore1.setText(Integer.toString(players.getPlayer1().getScore()));
                        winnerScore2.setText(Integer.toString(players.getPlayer2().getScore()));
                    }
                }
            }));
            }
        }
        timeline.play();
    }

}