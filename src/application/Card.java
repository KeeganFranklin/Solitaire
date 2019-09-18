package application;

import javafx.scene.paint.Color;

public class Card {
	
	private int value; 
	private Suit suit; 
	private String imageURL; 
	private Color color; 
	
	public Card(int val, Suit suit) {
		value = val; 
		this.suit = suit; 
		if (suit == Suit.DIAMOND || suit == Suit.HEART) {
			color = Color.RED; 
		} else {
			color = Color.BLACK; 
		}
	}
	
	public int getValue() {
		return value; 
	}
	
	public String getURL() {
		return imageURL; 
	}
	
	public Suit getSuit() {
		return suit; 
	}
	
	public Color getColor() {
		return color; 
	}
	
	public void setURL(String URL) {
		imageURL = URL; 
	}
	
	@Override 
	public String toString() {
		String result = value + " of " + suit;
		return result;
	}

}
