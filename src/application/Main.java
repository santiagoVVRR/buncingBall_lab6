package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			//BorderPane root = new BorderPane();
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//Parent root = FXMLLoader.load(getClass().getResource("juego.fxml"));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("juego.fxml"));
			Parent root = loader.load();
			
			ControllerClass cs = loader.getController();
			cs.setStage(stage);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					try {
						cs.onCloseRequest();
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			});
			
			Scene scene = new Scene(root);
			stage.setTitle("CATCH THE BALLS");
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
