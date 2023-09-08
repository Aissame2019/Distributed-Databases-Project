package application;
	
import java.io.IOException;
import java.util.Objects;
import application.Connection.DbConnectionLogin;
import application.Controllers.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;



public class Main extends Application {
	public static final String CURRENCY = "DH";
	public static Stage primaryStage;
	@Override
	public void start(Stage stage) {
		try {
			primaryStage = stage;
			Parent root = FXMLLoader.load(getClass().getResource("Views/LoginView.fxml")); 
			Scene primaryScene = new Scene(root);
			
			 primaryStage.setOnCloseRequest(event -> {
		            event.consume(); // Prevents the default close behavior

		            // Show a confirmation dialog
		            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		            alert.setTitle("Confirmation");
		            alert.setHeaderText("Are you sure you want to close?");
		            alert.setContentText("Click OK to close the application.");

		            // Handle the user's response
		            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
		            if (result == ButtonType.OK) {
		                // Set your object to null or perform any desired actions
		            	DbConnectionLogin.currentClient = null;
		            	ClientController.currProduct=null;
		                primaryStage.close();
		            }
		        });
			
			primaryStage.setScene(primaryScene); 
			primaryStage.setTitle("Souk Jemla");
			primaryStage.setWidth(1170);
			primaryStage.setHeight(650);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("application/asset/logo.png"));
			
			
			
			primaryStage.show(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void  changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        primaryStage.getScene().setRoot(pane);
    }
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
