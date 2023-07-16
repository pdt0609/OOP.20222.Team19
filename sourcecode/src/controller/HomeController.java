package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.board.Board;
import model.player.Player;
import myexception.InvalidNameException;
import model.player.Competitors;

public class HomeController {

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
        tutorialScreen.setVisible(false);
        nameScreen.setVisible(false);
        realName1.textProperty().bind(name1Box.textProperty());
        realName2.textProperty().bind(name2Box.textProperty());

        // Disable the Final Start button initially
        btnFinalStart.setDisable(false);

        btnFinalStart.setOnAction(event -> {
            System.out.println("Final Start button pressed");
            try {
                validateNames();
            } catch (InvalidNameException e) {
                // Show an alert with the custom exception message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Name");
                alert.setContentText(e.getMessage()); // 1, make exception, then throw then use it for handle exception
                alert.showAndWait();
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
        });
    }

    private void validateNames() throws InvalidNameException {
        String playerName1 = name1Box.getText();
        String playerName2 = name2Box.getText();

        if (playerName1.isEmpty() || playerName2.isEmpty()) {
            throw new InvalidNameException("Please enter both players' names!");
        } else if (playerName1.equals(playerName2) && !playerName1.isEmpty() && !playerName2.isEmpty()) {
            throw new InvalidNameException("Please enter different names for both players!");
        } else {
            startGame(playerName1, playerName2);
        }
    }

    private void startGame(String playerName1, String playerName2) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Play.fxml"));

            Board board = new Board();
            Player player1 = new Player(playerName1);
            Player player2 = new Player(playerName2);
            Competitors player = new Competitors(player1, player2, board);
            fxmlLoader.setController(new PlayController(player));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();

            // Set new scene for the new stage
            stage.setScene(new Scene(root));
            stage.setTitle("Play Screen");
            stage.show();

            // Close the current window (optional)
            Stage currentStage = (Stage) btnFinalStart.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
