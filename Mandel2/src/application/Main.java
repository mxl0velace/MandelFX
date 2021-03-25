package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Main extends Application {
	
	final static int MAXX = 1000;
	final static int MAXY = 800;
	final static int PLUSY = 200;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,MAXX,MAXY+PLUSY);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			System.out.println("Should be showing...");
						
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		System.out.println("Main thread ending!");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
