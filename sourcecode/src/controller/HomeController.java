package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.board.Board;
import model.player.Player;
import javafx.scene.Node;

public class HomeController {
    @FXML
    private Button btnStart;

    @FXML
    private void initialize() {
        btnStart.setOnAction(event -> {
            System.out.println("Start button pressed");
             // Close the current window (optional)
            Stage currentStage = (Stage) btnStart.getScene().getWindow();
            currentStage.close();

            //open a new window to start a game

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Play.fxml"));

                Board board = new Board();
                Player player = new Player("player1", "player2", board);
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
