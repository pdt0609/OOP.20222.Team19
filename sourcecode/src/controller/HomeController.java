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
import javafx.stage.Stage;
import model.board.Board;
import model.player.Players;
import javafx.scene.Node;

public class HomeController {
    private Board board;
    private Players player1;
    private Players player2;


	public HomeController (Board board, Players player1, Players player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }
    public HomeController() {
		// TODO Auto-generated constructor stub
	}
	@FXML
    private Button btnAccessHelp;	
	
	@FXML
    private Button btnStart;
	
	@FXML
	private Button btnExit;

    @FXML
    void btnAccessHelpClicked(ActionEvent event) {
        try {
            final String HELP_SCREEN_FILE_PATH = "/view/HelpScreen.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(HELP_SCREEN_FILE_PATH));
            fxmlLoader.setController(new HelpScreenController(board, player1, player2));
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
        btnStart.setOnAction(event -> {
            System.out.println("Start button pressed");
             // Close the current window (optional)
            Stage currentStage = (Stage) btnStart.getScene().getWindow();
            currentStage.close();

            //open a new window to start a game
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Play.fxml"));

                Board board = new Board();
                Players player = new Players("player1", "player2", board);
                fxmlLoader.setController(new PlayController(player, board));
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
    }
}
