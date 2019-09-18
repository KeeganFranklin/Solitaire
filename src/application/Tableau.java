package application;

import java.util.ArrayList;
import java.util.Stack;

import org.w3c.dom.Node;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class Tableau implements GamePile {
	
	private static String FACE_DOWN = "file:red_back.png";
	private static String EMPTY = "javagreen.jpg";
	private static Card FOUNDATION = null;
	private static int SPACING = 20; 
	
	private Pane pile; 
	private Stack<Card> cards; 
	private Stack<ImageView> images; 
	private ArrayList<ImageView> actualImages; 
	private int numVisible; 
	private Card top; 
	private int initialX; 
	private int initialY;
	private int height; 
	private int width; 
	
	
	public Tableau(int numCards, Deck deck, int startingX, int startingY, int cardHeight, int cardWidth) {
		cards = new Stack<>(); 
		cards.push(FOUNDATION);
		images = new Stack<>(); 
		ImageView base = placeCard(startingX, startingY, cardHeight, cardWidth, new Image(EMPTY)); 
		images.push(base);
		actualImages = new ArrayList<ImageView>(); 
		actualImages.add(base);
		numVisible = 1; 
		initialX = startingX; 
		initialY = startingY; 
		height = cardHeight; 
		width = cardWidth; 
		int count = 0; 
		while (count < numCards && deck.getNumLeft() > 0) {
			Card current = deck.drawOne(); 
			ImageView currentView = new ImageView(new Image(current.getURL()));
			images.push(currentView);
			count++; 
			cards.push(current);
			if (count == numCards) {
				actualImages.add(currentView);
			} else {
				actualImages.add(new ImageView(new Image(FACE_DOWN)));
			}
		}
		top = cards.peek();
	}

	@Override
	public void add(Card[] otherCards) {
		// [smallest -> greatest]
		for (int i = otherCards.length - 1; i >= 0; i--) {
			Card current = otherCards[i];
			cards.push(current);
			System.out.println(current + " This card's URL: + " + current.getURL());
			actualImages.add(new ImageView(new Image(current.getURL())));
			images.push(new ImageView(new Image(current.getURL())));
		}
		if (top == FOUNDATION) { // if pile was empty, top and bottom are now the same
			top = cards.peek();
		}
		numVisible += otherCards.length;
	}

	@Override
	public void display(Group root) {
		removePile(root);
		pile = new Pane(); 
		pile.setPickOnBounds(false);
		pile.setLayoutX(initialX);
		pile.setLayoutY(initialY);
		int x = initialX;
		int y = initialY; 
		int count = -1; 
		for (int image = 0; image < actualImages.size(); image++) {
			count++; 
			ImageView cardView = actualImages.get(image); 
			cardView = placeCard(x, y, height, width, cardView.getImage());
			pile.getChildren().add(cardView); 
			if (count > 0) {
				y += SPACING; 
			}
		}
		if (numVisible > 0 || actualImages.size() == 1) { // if pile is just foundation or has visible cards
			System.out.println("Clickability added for: " + top);
			pile.setOnMouseClicked((MouseEvent e) -> {
				SolitaireController.makeMove(this);
			});
		} else { // top most card 
			pile.setOnMouseClicked((MouseEvent e) -> {
				 flip(root);
			});
		}
		root.getChildren().add(pile);
	}
	
	private void removePile(Group root) {
		int index = 0; 
		boolean found = false;
		while (index < root.getChildren().size() && !found && pile != null) {
			javafx.scene.Node current = root.getChildren().get(index);
			if (current.equals(pile)) {
				found = true; 
				root.getChildren().remove(index); 
			}
			index++;
		}
	}
	
	private void flip(Group root) {
		numVisible = 1; 
		actualImages.remove(actualImages.size() - 1); 
		actualImages.add(new ImageView(new Image(top.getURL())));
		display(root);
	}
	
	@Override
	public Card[] remove(GamePile dest) {
		Card[] result; 
		if (dest.getClass().equals(FoundationPile.class)) {
			result = singleRemove();
		} else {
			result = pileRemove();
		}
		return result;
	}
	
	private Card[] singleRemove() {
		System.out.println("single remove");
		Card[] result = new Card[1];
		result[0] = cards.pop();
		images.pop(); 
		actualImages.remove(actualImages.size() - 1);
		numVisible--;
		top = cards.peek();
		return result; 
	}
	
	private Card[] pileRemove() {
		Card[] result = new Card[numVisible];
		int count = 0;
		// might be unnecessary to check for foundation
		while (count < numVisible && cards.peek() != FOUNDATION) {
			Card current = cards.pop();
			result[count] = current; 
			count++; 
			actualImages.remove(actualImages.size() - 1); 
			images.pop();
		}
		numVisible = 0;
		top = cards.peek();
		return result;
	}

	@Override
	public boolean canAdd(GamePile other) {
		System.out.println(other.getTop());
		System.out.println(getBottom());
		final int KING = 13;
		boolean otherHasTop = other.getTop() != null;
		boolean firstCase = top == FOUNDATION && otherHasTop && other.getTop().getValue() == KING;
		boolean colorsMatch = otherHasTop && top != FOUNDATION && other.getTop().getColor().equals(getBottom().getColor());
		boolean secondCase = top != FOUNDATION && otherHasTop && !colorsMatch && other.getTop().getValue() + 1 == getBottom().getValue();
		return firstCase || secondCase; 
	}

	@Override
	public Card getTop() {
		return top;
	}
	
	public Card getBottom() {
		return cards.peek();
	}
	
	private static ImageView placeCard(int layoutX, int layoutY, int cardHeight, int cardWidth, Image image) {
		ImageView card = new ImageView();
		card.setImage(image);
		card.setLayoutX(layoutX);
		card.setLayoutY(layoutY);
		card.setFitHeight(cardHeight);
		card.setFitWidth(cardWidth);
		return card; 
	}
	
	public String toString() {
		return cards.toString();
	}
}
