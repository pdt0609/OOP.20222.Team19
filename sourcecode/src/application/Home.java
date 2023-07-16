package application;

import controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Home extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Name for the application
        final String appName = "O An Quan App";
        primaryStage.setTitle(appName);

        // Set icon for the application
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/view/images/icon.png")));

        // Set minimum window size to avoid unusual look
        primaryStage.setMinHeight(768);
        primaryStage.setMinWidth(1024);

        FXMLLoader loader = new FXMLLoader();
        // Load view for the stage
        loader.setLocation(getClass().getResource("/view/Home.fxml"));
        Parent rootLayout = loader.load();
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        HomeController homeController = loader.getController();
        homeController.initialize(); // Call initialize method to set up the controller

        // Display the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
