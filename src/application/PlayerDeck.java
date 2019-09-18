package application;

import java.util.Stack;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/* 
 */
public class PlayerDeck implements GamePile {
	
	private static String FACE_DOWN = "file:red_back.png"; 
	private static String EMPTY_TOP = "javagreen.jpg";
	private static String EMPTY_PILE = "foundationPile.png"; 
	
	private Card top;
	private Stack<Card> pile; 
	private Stack<Card> discard;
	private ImageView topView; 
	private ImageView pileView; 
	private int topX; 
	private int topY; 
	private int pileX; 
	private int pileY; 
	private int height; 
	private int width; 

	public PlayerDeck(Deck cards, int startingX, int startingY, int cardHeight, int cardWidth) {
		final int SPACING = cardWidth + 20;
		pile = new Stack<>(); 
		discard = new Stack<>();
		pileX = startingX; 
		pileY = startingY;
		topX = startingX + SPACING;
		topY = startingY; 
		height = cardHeight; 
		width = cardWidth; 
		while (cards.getNumLeft() > 0) {
			pile.push(cards.drawOne());
		}
		pileView = new ImageView(new Image(FACE_DOWN)); 
		topView = placeCard(topX, topY, height, width, EMPTY_TOP);
		top = null; 
	}
	@Override
	public void add(Card[] cards) {
		// canAdd will never be true, so this method
		// will never be called		
	}

	@Override
	public void display(Group root) {
		displayPile(root); 
		displayTop(root);
	}
	
	private void displayPile(Group root) {
		if (pile.size() > 0) {
			pileView = placeCard(pileX, pileY, height, width, FACE_DOWN);
			pileView.setOnMouseClicked((MouseEvent e) -> {
				if (top != null) {
					discard.push(top); 
				}
				top = pile.pop();
				topView = placeCard(topX, topY, height, width, top.getURL());
				displayTop(root);
				displayPile(root);
			});
			root.getChildren().add(pileView);
		} else {
			pileView = placeCard(pileX, pileY, height, width, EMPTY_PILE);
			pileView.setOnMouseClicked((MouseEvent e) -> {
				refill(); 
				if (!pile.isEmpty()) {
					displayPile(root);
				} 
			});
			root.getChildren().add(pileView);
		}
	}
	
	private void refill() {
		while (!discard.isEmpty()) {
			pile.push(discard.pop());
		}
	}
	
	private void displayTop(Group root) {
		topView.setOnMouseClicked((MouseEvent e) -> {
			SolitaireController.makeMove(this);
		});
		root.getChildren().add(topView);
	}
	
	@Override
	public Card[] remove(GamePile dest) {
		// destination doesn't matter
		Card[] cards = new Card[1]; 
		cards[0] = top; 
		top = null; 
		topView = placeCard(topX, topY, height, width, EMPTY_TOP); 
		return cards; 
	}

	@Override
	public boolean canAdd(GamePile other) {
		return false;
	}

	@Override
	public Card getTop() {
		return top; 
	}
	
	private static ImageView placeCard(int layoutX, int layoutY, int cardHeight, int cardWidth, String url) {
		ImageView card = new ImageView(new Image(url));
		card.setLayoutX(layoutX);
		card.setLayoutY(layoutY);
		card.setFitHeight(cardHeight);
		card.setFitWidth(cardWidth);
		card.setPickOnBounds(true);
		return card; 
	}

}
