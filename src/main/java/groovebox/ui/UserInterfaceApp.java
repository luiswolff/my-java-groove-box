package groovebox.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserInterfaceApp extends Application {
	@Override
	public void start(Stage primaryStage)  throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("groove-box-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 400, 200);
		GrooveBoxController controller = fxmlLoader.getController();
		primaryStage.setOnCloseRequest(event -> controller.close());
		primaryStage.setTitle("Groove");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
