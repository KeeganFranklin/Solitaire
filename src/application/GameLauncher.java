package application;
	
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class GameLauncher extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		Group root = new Group(); 
		placeTitle(root); 
		placePlayButton(root); 
		showStage(primaryStage, root); 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static void placeTitle(Group root) {
//		List<String> ye = Font.getFamilies();
//		System.out.print(ye);
		Text title = new Text(); 
		title.setText("Solitaire");
		// Fix font
		title.setFont(Font.font("Big Caslon",FontWeight.BOLD, 30));
		title.setX(95);
		title.setY(50);
		root.getChildren().add(title);
	}
	
	private static void placePlayButton(Group root) {
		Button playButton = new Button();
		playButton.setText("New Game");
		playButton.setLayoutX(110);
		playButton.setLayoutY(200);
		playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				Stage solitaireStage = new Stage(); 
			}
		});
		root.getChildren().add(playButton);
	}
	
	private static void showStage(Stage primaryStage, Group root) {
		Scene scene = new Scene(root, 300, 300);
		scene.setFill(Color.LIMEGREEN);
		primaryStage.setScene(scene);
	    primaryStage.setTitle("Solitaire");
	    primaryStage.show(); 
	}
}
