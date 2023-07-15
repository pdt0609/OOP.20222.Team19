// package controller;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Random;

// import javafx.animation.KeyFrame;
// import javafx.animation.Timeline;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Node;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.AnchorPane;
// import javafx.scene.layout.Pane;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;
// import javafx.util.Duration;
// import model.board.Board;
// import model.board.Cell;
// import model.player.Players;
// import javafx.animation.Animation;
// import javafx.animation.KeyFrame;
// import javafx.animation.Timeline;
// import javafx.util.Duration;

// public class PlayController{

//     @FXML
//     private Button btnHelp;

//     @FXML
//     private Button btnHome;

//     @FXML
//     private Pane cell00;

//     @FXML
//     private Pane cell01;

//     @FXML
//     private Pane cell02;

//     @FXML
//     private Pane cell03;

//     @FXML
//     private Pane cell04;

//     @FXML
//     private Pane cell05;

//     @FXML
//     private Pane cell06;

//     @FXML
//     private Pane cell07;

//     @FXML
//     private Pane cell08;

//     @FXML
//     private Pane cell09;

//     @FXML
//     private Pane cell10;

//     @FXML
//     private Pane cell11;

//     @FXML
//     private Text scorePlayer1;

//     @FXML
//     private Text scorePlayer2;

//     @FXML
//     private ImageView turnPlayer1;

//     @FXML
//     private ImageView turnPlayer2;

//     @FXML
//     private Button btnHomeWinner;

//     @FXML
//     private Button btnPlayAgain;


//     @FXML
//     private AnchorPane endGameScreen;

//     @FXML
//     private Text winnerName;

//     @FXML
//     private String player1Name;

//     @FXML
//     private String player2Name;

//     @FXML
//     private Text name1Display;

//     @FXML
//     private Text name2Display;

//     @FXML
//     private Text winnerScore1;

//     @FXML
//     private Text winnerScore2;

//     @FXML
//     private Text player1Winner;

//     @FXML
//     private Text player2Winner;

//     @FXML
//     private AnchorPane helpScreen;

//     @FXML
//     private Button btnContinue;

//     @FXML
//     private Text name1;

//     @FXML
//     private Text name2;





//     private Timeline timeline = new Timeline() ;
//     private List<Pane> paneList; // not exist -> need to declare
//     private Players players;
//     private static Board board;
//     int numberOfCells;

//     public PlayController(Players players, Board board) {
//         this.players = players;
//         this.board = board;
//         this.numberOfCells = board.getNumSquares() +board.getNumHalfCircles();
//         this.player1Name = players.getPlayer1();
//         this.player2Name = players.getPlayer2();
//     }



//     // @FXML
//     // public void btnBackFromHomeControllerClicked(ActionEvent event) {
        
//     // }

//     @FXML
//     public void initialize() {

//         helpScreen.setVisible(false);
        
//         name1.setText(player1Name);
//         name2.setText(player2Name);

//         btnHelp.setOnAction(event -> {
//             helpScreen.setVisible(true);
//         });

//         btnContinue.setOnAction(event -> {
//             helpScreen.setVisible(false);
//         });

//         btnHome.setOnAction(event -> {

//             try {
//                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
//                 Parent root = fxmlLoader.load();
//                 Stage stage = new Stage();
//                 stage.setScene(new Scene(root));
//                 stage.setTitle("Home Screen");
//                 stage.show();
        
//                 // Close the current stage (optional)
//                 Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                 currentStage.close();
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         });
        
        


//         //set 2 frame invisible
//         turnPlayer1.setVisible(false);
//         turnPlayer2.setVisible(false);
//         paneList = Arrays.asList(cell00, cell01, cell02, cell03, cell04,cell05,cell06,cell07,cell08,cell09,cell10,cell11);

//         //hide direction initially
//         for (int i=0; i < numberOfCells; i++){
//             Pane pane = paneList.get(i);
//             List<Node> children = pane.getChildren();
//             for (Node child : children) {
//                 if (child instanceof ImageView) {
//                     ImageView imageView = (ImageView) child;
//                     imageView.setVisible(false);
//                 }
//             }
//         }
//         this.setDisplay(board);

//         //set play again button
//         btnPlayAgain.setOnAction(event ->{
//             endGameScreen.setVisible(false);
//             //reset board
//             board = new Board();
//             players = new Players(player1Name,player2Name, board);
//             initialize();
//             //display
//             this.setDisplay(board);
//         });

//         //set home button
//         btnHomeWinner.setOnAction(event ->{
//             try {
//                 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
//                 Parent root = fxmlLoader.load();
//                 Stage stage = new Stage();
//                 stage.setScene(new Scene(root));
//                 stage.setTitle("Home Screen");
//                 stage.show();
        
//                 // Close the current stage (optional)
//                 Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                 currentStage.close();
//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//             });
//         endGameScreen.setVisible(false);

//         //set cell clickable and set cell around is disable
        
//         for (int i=0; i < numberOfCells; i++) {
//             int index = i;
//             if ((i != 0) && (i != 6)){
//                 Pane pane = paneList.get(i);
//                 pane.setOnMouseClicked(event -> {

//                     if (board.getCells()[index].getGemList().size() == 0){
//                         pane.setDisable(false);
//                         System.out.println("Cell could not be clicked");
//                         if (players.getTurn() == 1){
//                             if (board.checkNoGemsOnSide(player1Name)){
//                                 if (players.getScore(player1Name) >= 5){
//                                     players.reduceScore(player1Name);
//                                     this.setDisplay(board);
//                                 }
//                                 else if (players.getScore(player1Name) < 5 && players.getScore(player2Name) >= 5){
//                                     players.borrow(player1Name, player2Name);
//                                     this.setDisplay(board);
                        
//                                 }
//                                 // do not consider if both players have score < 5, it is impossible
//                             }

//                         }
//                         else if (players.getTurn() == 2){
//                             if (board.checkNoGemsOnSide(player2Name)){
//                                 if (players.getScore(player2Name) >= 5){
//                                     players.reduceScore(player2Name);
//                                     this.setDisplay(board);
//                                 }
//                                 else if (players.getScore(player2Name) < 5 && players.getScore(player1Name) >= 5){
//                                     players.borrow(player2Name,player1Name);
//                                     this.setDisplay(board);
//                                 }
//                             }
//                         }
//                     }


//                     else{
//                         System.out.println("Cell clicked");
                        
//                         //show direction
//                         showDirection(pane);
                        
//                         for (int j=0; j < numberOfCells ; j++){
//                             if (j != index ){
//                                 Pane paneAround = paneList.get(j);
//                                 paneAround.setDisable(true);
//                             }
//                         }
//                     }
//                 });
//             }
//         }

//         //set direction then spread gems of player 1
//         for (int i=0; i < numberOfCells; i++){
//             int index = i;
//             if ((i != 0) && (i != 6)){
//                 Pane pane = paneList.get(index);
//                 List<Node> children = pane.getChildren();
//                 for (Node child : children) {
//                     if (child instanceof ImageView) {
//                         ImageView imageView = (ImageView) child;

//                         imageView.setOnMouseClicked(event1 -> {
//                             System.out.println("Direction clicked");

//                             // Set the direction
//                             if (imageView.getId().startsWith("btnCCL")) {
//                                 players.setDirection(0);
//                             } else if (imageView.getId().startsWith("btnCL")) {
//                                 players.setDirection(1);
//                             }
//                             System.out.println(player1Name + "before spread" + players.getScore(player1Name) + " " + player2Name + " " + players.getScore(player2Name));

//                             //spread gems
//                             if (players.getTurn() == 1){
//                                 players.spreadGems(player1Name,board.getCells()[index], players.getDirection());
//                                 System.out.println(players.getPlayer1() + " after spread main" + players.getScore("player1") + " " + "player2" + " " + players.getScore("player2"));

//                             }
//                             else if (players.getTurn() == 2){
//                                 players.spreadGems(player2Name,board.getCells()[index], players.getDirection());
//                             }

//                             //fake end game
//                             // board.getCells()[0].setEmpty();
//                             // board.getCells()[6].setEmpty();

//                             //check end game (inside)
//                             // if (board.endGame()){
//                             //     System.out.println("end game");
//                             //     // players.assembleSmallGems(players.getItinerary());
//                             //     if (players.getScore(player1Name) > players.getScore(player2Name)){
//                             //         winnerName.setText("1");
//                             //     }
//                             //     else if (players.getScore(player1Name) < players.getScore(player2Name)){
//                             //         winnerName.setText("2");
//                             //     }
//                             //     else{
//                             //         winnerName.setText("draw");
//                             //     }
//                             //     winnerScore1.setText(Integer.toString(players.getScore(player1Name)));
//                             //     winnerScore2.setText(Integer.toString(players.getScore(player2Name)));

//                             // }

//                             // for (Cell cell : players.getItinerary()){
//                             //     System.out.println("location" + cell.getLocation() + "size" + cell.getGemList().size());
//                             // }
//                             System.out.println(player1Name + "after print itinerary " + players.getScore(player1Name) + " " + player2Name + " " + players.getScore(player2Name));
//                             //display number of gems
//                             // this.setMotionDisplay(players.getItinerary(), pane);

//                             // players.setItinerary(new ArrayList<Cell>());
 
//                             event1.consume();
//                         });


//                     }
//                 }
//             }
//         }

//         // Initialize play
//         Random rand = new Random();
//         int randTurn = rand.nextInt(2) + 1;
//         players.setTurn(1);
//         if (players.getTurn() == 1){

//             turnPlayer1.setVisible(true);
//             turnPlayer2.setVisible(false);
//             //set able for cells 1
//             for (int i=0; i < numberOfCells; i++) {
//                 if ((i != 0) && (i != 6)){
//                     Pane pane = paneList.get(i);
//                     if (i < 6)
//                         pane.setDisable(false);
//                     else
//                         pane.setDisable(true);
//                 }
//             }
            
            

//         }else{
//             turnPlayer1.setVisible(false);
//             turnPlayer2.setVisible(true);
//             //set disable for cells 1
//             for (int i=0; i < numberOfCells; i++) {
//                 if ((i != 0) && (i != 6)){
//                     Pane pane = paneList.get(i);
//                     if (i < 6)
//                         pane.setDisable(true);
//                     else
//                         pane.setDisable(false);
//                 }
//             }
            
            
//         }

//     }

//     public void switchTurn(Pane paneChosen){
        

//         if (players.getTurn() == 1){

//             // display turn
//             turnPlayer1.setVisible(false);
//             turnPlayer2.setVisible(true);

//             for (int i=0; i < numberOfCells; i++) {
//                 if ((i != 0) && (i != 6)){
//                     Pane pane = paneList.get(i);
//                     if (i < 6)
//                         pane.setDisable(true);
//                     else
//                         pane.setDisable(false);
//                 }
//             }
//             //set new turn
//             players.setTurn(2);
        
//         }else{

//             // display turn
//             turnPlayer1.setVisible(true);
//             turnPlayer2.setVisible(false);
            
//             for (int i=0; i < numberOfCells; i++) {
//                 if ((i != 0) && (i != 6)){
//                     Pane pane = paneList.get(i);
//                     if (i < 6)
//                         pane.setDisable(false);
//                     else
//                         pane.setDisable(true);
//                 }
//             }
//             //set new turn
//             players.setTurn(1);
//             }
//         }
    
//     public void showDirection(Pane pane) {
//         // Retrieve the children of the Pane
//         List<Node> children = pane.getChildren();
//         // Loop through the children of the Pane
//         for (Node child : children) {
//             // Check if the child is an ImageView
//             if (child instanceof ImageView) {
//                 ImageView imageView = (ImageView) child;
//                 // Set the visibility of the ImageView to true
//                 imageView.setVisible(true); 
//             }
//         }
//     }

//     public void setDisplay(Board board){
//         for (int i=0; i < board.getCells().length; i++){
//             Pane pane = paneList.get(i);
//             for (Node child : pane.getChildren()) {
//                 if (child instanceof Text) {
//                     Text text = (Text) child;
//                     if (child.getId().startsWith("numGems")) {
//                         text.setText(Integer.toString(board.getCells()[i].getGemList().size()));
//                     }if (child.getId().startsWith("small")) {
//                         text.setText("*".repeat(board.getCells()[i].getNumberOfSmallGems()));
//                     }
//                     if (child.getId().startsWith("big")) {
//                         text.setText("*".repeat(board.getCells()[i].getNumberOfBigGems()));
//                     }
//                 }
//             }

//         }
//         scorePlayer2.setText(Integer.toString(players.getScore(player2Name)));
//         scorePlayer1.setText(Integer.toString(players.getScore(player1Name)));
//     }


//     public void setMotionDisplay(List<Cell> itinerary, Pane paneChosen) {
    
//         List<Node> children = paneChosen.getChildren();
//         // Set both direction buttons in the pane to invisible
//         for (Node child : children) {
//             if (child instanceof ImageView) {
//                 child.setVisible(false);
//             }
//         }

//         int longDisplay = itinerary.size() ;
//         if (!itinerary.isEmpty()) { // Add a check to avoid accessing an empty list
//             // int i = 0;
//             timeline.getKeyFrames().clear();
//             for (int i = 0; i < longDisplay + 2; i++){
//                 int index = i;
                
//                 timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(i*0.5), event -> { // time to display
//                 if (!board.endGame()){
//                     if (index < longDisplay){
//                         Cell cell = itinerary.get(index);
//                         System.out.println("Run location" + cell.getLocation() + "Run size" + cell.getGemList().size());

//                         int id = cell.getLocation();
//                         Pane pane = paneList.get(id);

//                         for (Node child : pane.getChildren()) {
//                             if (child instanceof Text) {
//                                 Text text = (Text) child; //downcasting
//                                 if (child.getId().startsWith("numGems")) {
//                                     text.setText(Integer.toString(cell.getGemList().size()));
//                                 }if (child.getId().startsWith("small")) {
//                                     text.setText("*".repeat(cell.getNumberOfSmallGems()));
//                                 }
//                                 if (child.getId().startsWith("big")) {
//                                     text.setText("*".repeat(cell.getNumberOfBigGems()));
//                                 }
//                             }
//                         }
                    
                        

//                     }
//                     if (index == longDisplay){
//                         switchTurn(paneChosen);
//                         scorePlayer1.setText(Integer.toString(players.getScore(player1Name)));
//                         scorePlayer2.setText(Integer.toString(players.getScore(player2Name)));
//                     }
//                 }

//                 if (board.endGame()){ // it actually always true because results were computed before
//                     Cell cell = itinerary.get(index);
//                     System.out.println("Run location" + cell.getLocation() + "Run size" + cell.getGemList().size());

//                     int id = cell.getLocation();
//                     Pane pane = paneList.get(id);

//                     for (Node child : pane.getChildren()) {
//                         if (child instanceof Text) {
//                             Text text = (Text) child; //downcasting
//                             if (child.getId().startsWith("numGems")) {
//                                 text.setText(Integer.toString(cell.getGemList().size()));
//                             }if (child.getId().startsWith("small")) {
//                                 text.setText("*".repeat(cell.getNumberOfSmallGems()));
//                             }
//                         }
//                     }
//                     scorePlayer1.setText(Integer.toString(players.getScore(player1Name)));
//                     scorePlayer2.setText(Integer.toString(players.getScore(player2Name)));
//                     endGameScreen.setVisible(true);
//                     players.resetCreditHistory();
//                 }
                
                
//             }));
            
//             }

//         }
//         // timeline.setCycleCount(itinerary.size());
//         timeline.play();

//     }
        
        
    
    

    

    

// }
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import model.board.Board;
import model.board.Cell;
import model.player.Players;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

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

    @FXML
    private AnchorPane helpScreen;

    private Timeline timeline = new Timeline() ;
    private List<Pane> paneList; // not exist -> need to declare
    private Players players;
    private static Board board;
    int numberOfCells;

    public PlayController(Players players, Board board) {
        this.players = players;
        this.board = board;
        this.numberOfCells = board.getNumSquares() +board.getNumHalfCircles();
        this.player1Name = players.getPlayer1();
        this.player2Name = players.getPlayer2();
    }

    // @FXML
    // void btnAccessHelpClicked(ActionEvent event) {

    //         Stage currentStage = (Stage) btnHome.getScene().getWindow();
    //         currentStage.close();
    //     try {
    //         final String HELP_SCREEN_FILE_PATH = "/view/HelpScreen.fxml";
    //         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(HELP_SCREEN_FILE_PATH));
    //         fxmlLoader.setController(new HelpScreenController());
    //         Parent root = fxmlLoader.load();
    //         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    //         // set new scene for current stage
    //         stage.setScene(new Scene(root));
    //         stage.setTitle("Help Screen");
    //         stage.show();

    //     } catch (IOException e) {
    //         e.printStackTrace();

    //     }

    // }

    // @FXML
    // public void btnBackFromHomeControllerClicked(ActionEvent event) {
    //     Stage currentStage = (Stage) btnHome.getScene().getWindow();
    //     currentStage.close();
    //     try {
    //         // TODO while playing: just a pop-up

    //         final String INTRO_SCREEN_FILE_PATH = "/view/Home.fxml";

    //         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(INTRO_SCREEN_FILE_PATH));
    //         Parent root = fxmlLoader.load();
    //         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    //         stage.setScene(new Scene(root));
    //         stage.setTitle("Intro Screen");

    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

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
        this.setDisplay(board);

        //set play again button
        btnPlayAgain.setOnAction(event ->{
            endGameScreen.setVisible(false);
            //reset board
            board = new Board();
            players = new Players(player1Name,player2Name, board);
            initialize();
            //display
            this.setDisplay(board);
        });

        helpScreen.setVisible(false);

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
                            this.setDisplay(board);
                            }

                        }
                        else if (players.getTurn() == 2){
                            if (board.checkNoGemsOnSide(player2Name)){
                            players.reduceScore(player2Name);
                            this.setDisplay(board);
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
        
                                // System.out.println(itin.size());
                            }
                            if(players.getTurn() == 2){
                                players.spreadGems(player2Name,board.getCells()[index], players.getDirection());
                                // System.out.println(itin.size());
                            }

                            //fake end game
                            // board.getCells()[0].setEmpty();
                            // board.getCells()[6].setEmpty();

                            //check end game
                            if (board.endGame()){
                                System.out.println("end game");
                                // players.assembleSmallGems();
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
                            for (Cell cell : players.getItinerary()){
                                System.out.println("location" + cell.getLocation() + "size" + cell.getGemList().size());
                            }

                            //display number of gems
                            // System.out.println("size of itinerary"+players.getItinerary().size());
                            this.setMotionDisplay(players.getItinerary(),pane);
                            players.setItinerary(new ArrayList<Cell>());
                            // players.setItinerary(new ArrayList<Cell>());
                            // this.setDisplay(board);
                            
                            
                             // still have error when click to direction it not change turn, 3 time after and also not invisible
                            // System.out.println(Integer.toString(players.getTurn()));
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
        scorePlayer2.setText(Integer.toString(players.getScore(player2Name)));
        scorePlayer1.setText(Integer.toString(players.getScore(player1Name)));
    }


    public void setMotionDisplay(List<Cell> itinerary, Pane paneChosen) {
        // int delayMilliseconds = 2000; // Delay between each cell update
        // int intermediateFrames = 10; // Number of intermediate frames between each cell update
        // int totalFrames = itinerary.size() * (intermediateFrames + 1); // Total number of frames
        List<Node> children = paneChosen.getChildren();

        // Set both direction buttons in the pane to invisible
        for (Node child : children) {
            if (child instanceof ImageView) {
                child.setVisible(false);
            }
        }
        int longDisplay = itinerary.size() ;
        if (!itinerary.isEmpty()) { // Add a check to avoid accessing an empty list
            // int i = 0;
            timeline.getKeyFrames().clear();
            for (int i = 0; i < longDisplay; i++){
                int index = i;
                timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(i*0.5), event -> {
                Cell cell = itinerary.get(index);
                System.out.println("Run location" + cell.getLocation() + "Run size" + cell.getGemList().size());
                int id = cell.getLocation();
                Pane pane = paneList.get(id);

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
                if (index == itinerary.size()-1){
                    scorePlayer2.setText(Integer.toString(players.getScore(player2Name)));
                    scorePlayer1.setText(Integer.toString(players.getScore(player1Name)));
                    switchTurn(paneChosen);
                    timeline.stop();
                }
                

            }));
                
            }

        }
        
        // timeline.setCycleCount(itinerary.size());
        timeline.play();

    }
        
        
    
    

    

    

}


