package application;

import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SolitaireController extends Application {
	
	private static int NUM_TABLEAUS = 7; 
	private static int NUM_FOUNDATION_PILES = 4;
	private static int APP_WIDTH = 1440; 
	private static int APP_HEIGHT = 800; 
	private static int CARD_WIDTH = 100; 
	private static int CARD_HEIGHT = 125;
	
	private Deck deck; 
	private FoundationPile[] foundationPiles; 
	private PlayerDeck playerDeck;
	private Tableau[] tableauPiles; 
	
	private static Group root;
	private static boolean moveInPlay; 
	private static GamePile prevPile; 

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new Group(); 
		deck = new Deck(); 
		setUpImages(); 
		createLayout(); 
		displayLayout();
		Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
		scene.setFill(Color.GREEN);
		primaryStage.setTitle("Solitaire");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void createLayout() {
		foundationPiles = new FoundationPile[NUM_FOUNDATION_PILES];
		int x = 950;
		int y = 10; 
		for (int i = 0; i < foundationPiles.length; i++) {
			foundationPiles[i] = new FoundationPile(x, y, CARD_HEIGHT, CARD_WIDTH); 
			final int SPACING = 20; 
			x += CARD_WIDTH + SPACING; 
		}
		x = 0; 
		y = APP_HEIGHT / 5; // magic numbers 
		tableauPiles = new Tableau[NUM_TABLEAUS];
		for (int i = 0; i < tableauPiles.length; i++) {
			final int SPACING = CARD_WIDTH + 10;
			tableauPiles[i] = new Tableau((i + 1), deck, x, y, CARD_HEIGHT, CARD_WIDTH);
			x +=  SPACING;
		}
		Deck secondaryDeck = new Deck(deck);
		x = 10; 
		y = 10; 
		playerDeck = new PlayerDeck(secondaryDeck, x, y, CARD_HEIGHT, CARD_WIDTH);
	}
	
	private void displayLayout() {
		for (int i = 0; i < foundationPiles.length; i++) {
			foundationPiles[i].display(root);
		}
		playerDeck.display(root);
		for (int i = 0; i < tableauPiles.length; i++) {
			tableauPiles[i].display(root);
		}
	}
	
	public static void makeMove(GamePile first) {
		System.out.println("move initiated");
		if (!moveInPlay) {
			moveInPlay = true; 
			prevPile = first; 
			System.out.println("move initiated on " + first.getTop());
		} else {
			System.out.println("result of trying move: " + first.canAdd(prevPile));
			if (first.canAdd(prevPile)) {
				first.add(prevPile.remove(first));
				System.out.println("move made, about to display");
				prevPile.display(root);
				first.display(root);
			}
			moveInPlay = false; 
			prevPile = null;
		}
		// checkForWin()
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void setUpImages() {
		String start = "file:"; 
		String end = ".png"; 
		final int TOTAL_CARDS = deck.getNumLeft();
		for (int i = 0; i < TOTAL_CARDS; i++) {
			Card current = deck.drawOne();
			if (current.getSuit() == Suit.HEART) {
				current.setURL(start + "" + current.getValue() + "H" + end);
			} else if (current.getSuit() == Suit.CLUB) {
				current.setURL(start + current.getValue() + "C" + end);
			} else if (current.getSuit() == Suit.DIAMOND) {
				current.setURL(start + current.getValue() + "D" + end);
			} else { // spade
				current.setURL(start + current.getValue() + "S" + end);
			}
		}
		deck.refill();
	}

}
