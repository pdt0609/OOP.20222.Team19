import controller.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception { //make stage, make loader to load view, make scene, make controller, set controller to loader, set scene to stage, show stage
        // Name for the application
        final String appName = "O An Quan Home Screen";
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

        // Load model for view through its controller
        HomeController homeController = loader.getController();
        loader.setController(homeController);

        // Display the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





