package application;

import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/* How to handle null pointer exceptions? 
 */
public class FoundationPile implements GamePile {
	
	private static String EMPTY_PILE = "foundationpile.png"; 
	
	private int x; 
	private int y; 
	private int height; 
	private int width; 
	private Card top; 
	private ImageView topView; 
	private Stack<Card> cards; 
	private Stack<ImageView> images; 
	
	public FoundationPile(int startingX, int startingY, int cardHeight, int cardWidth) {
		cards = new Stack<>(); 
		images = new Stack<>();
		top = null; 
		cards.add(null);
		ImageView empty = new ImageView(new Image(EMPTY_PILE));
		images.add(empty); 
		topView = empty; 
		height = cardHeight; 
		width = cardWidth;
		x = startingX; 
		y = startingY;
	}

	@Override
	public void add(Card[] cards) {
		add(cards[0]); 
	}
	
	// other game pile has already been accepted
	public void add(Card card) {
		ImageView newTop = new ImageView(new Image(card.getURL()));
		images.push(newTop); 
		cards.push(card);
		top = card; 
		topView = newTop; 
	}

	@Override
	public void display(Group root) {
		topView.setLayoutX(x);
		topView.setLayoutY(y);
		topView.setFitHeight(height);
		topView.setFitWidth(width);
		topView.setPickOnBounds(true);
		topView.setOnMouseClicked((MouseEvent e) -> {
			SolitaireController.makeMove(this);
		});
		root.getChildren().add(topView);
	}

	@Override
	public Card[] remove(GamePile dest) {
		// destintation doesn't matter
		Card[] result = new Card[1]; 
		result[0] = cards.pop();
		images.pop(); 
		topView = images.peek(); 
		top = cards.peek(); 
		return result; 
	}
	
	// clean up	
	@Override
	public boolean canAdd(GamePile other) {
		if (other.getClass().equals(Tableau.class)) {
			Card compare = ((Tableau) other).getBottom();
			System.out.println("compare: " + compare + " foundation's top: " + top);
			return compare != null && (top == null && compare.getValue() == 1) || 
					(top != null && compare.getSuit() == top.getSuit() && top.getValue() + 1 == compare.getValue());
		} else {
			return (top == null && other.getTop().getValue() == 1) || 
					(other.getTop().getSuit() == top.getSuit() && top.getValue() + 1 == other.getTop().getValue());
		}
	}
	
	@Override
	public Card getTop() {
		return top; 
	}
	
	public boolean pileFinished() {
		final int KING = 13;
		return top != null && top.getValue() == KING;
	}
}
