package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.board.Board;
import model.player.Player;
import model.player.Competitors;
import javafx.scene.Node;

public class HomeController {

    public HomeController() {}
	@FXML
    private Button btnAccessHelp;	
	
	@FXML
	private Button btnExit;

    @FXML 
    private Button btnBack;

    @FXML
    private Button btnFinalStart;

    @FXML
    private Button btnStart;

    @FXML
    private ImageView helpScreen;

    @FXML
    private TextField name1Box;

    @FXML
    private TextField name2Box;

    @FXML
    private AnchorPane nameScreen;

    @FXML
    private Label realName1;

    @FXML
    private Label realName2;

    @FXML
    private AnchorPane tutorialScreen;


    
    @FXML
    void btnExitGameClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Exit Game");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> res = alert.showAndWait();
        if(res.get() == ButtonType.OK) {
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
        tutorialScreen.setVisible(false);
        nameScreen.setVisible(false);
        realName1.textProperty().bind(name1Box.textProperty());
        realName2.textProperty().bind(name2Box.textProperty());

        btnFinalStart.setOnAction(event -> {
            System.out.println("Final Start button pressed");
             // Close the current window (optional)
            Stage currentStage = (Stage) btnStart.getScene().getWindow();
            currentStage.close();

            //open a new window to start a game
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Play.fxml"));

                Board board = new Board();
                Player player1 = new Player(name1Box.getText()); // need to enter to avoid mistake same " ", 
                Player player2 = new Player(name2Box.getText());//can add exception
                Competitors player = new Competitors(player1, player2, board);
                fxmlLoader.setController(new PlayController(player));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set new scene for current stage
                stage.setScene(new Scene(root));
                stage.setTitle("Play Screen");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btnAccessHelp.setOnAction(event -> {
            System.out.println("Help button pressed");
            tutorialScreen.setVisible(true);
        });

        btnBack.setOnAction(event -> {
            System.out.println("Back button pressed");
            tutorialScreen.setVisible(false);
        });

        btnStart.setOnAction(event -> {
            System.out.println("Start button pressed");
            nameScreen.setVisible(true);
            
        });}
    public TextField getName1Box() {
        return name1Box;

    }

    public TextField getName2Box() {
        return name2Box;
    }
}

