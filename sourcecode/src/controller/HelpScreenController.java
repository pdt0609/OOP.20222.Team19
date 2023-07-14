package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.board.Board;
import model.player.Players;

import java.io.IOException;

public class HelpScreenController {
    private Board board;
    private Players player1, player2;
    public HelpScreenController() {
    	
    }
    public HelpScreenController(Board board, Players player1, Players player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
    }

    @FXML
    private Button btnBackFromHelpScreen;

    @FXML
    void btnBackFromHelpScreenClicked(ActionEvent event) {
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
}
