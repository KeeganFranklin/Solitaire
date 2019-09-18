package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Deck {
	
	private static final int NUM_CARDS_IN_DECK = 52; 
	
	private Card[] deck; 
	private int numLeft; 
	private int currentIndex;
	
	// DONT USE CARDS, JUST DECK
	public Deck() {
		Card[] cards = new Card[NUM_CARDS_IN_DECK];
		Suit[] suits = {Suit.CLUB, Suit.DIAMOND, Suit.HEART, Suit.SPADE};
		currentIndex = 0; 
		for (int val = 1; val < 14; val++) {
			for (int i = 0; i < 4; i++) {
				Card add = new Card(val, suits[i]);
				cards[currentIndex] = add;
				currentIndex++;
			}
			
		}
		numLeft = NUM_CARDS_IN_DECK;
		deck = cards;
		currentIndex--; 
		shuffle();
	}
	
	//FIX CURRENTINDEX, START AT -1
	public Deck(Deck cards) {
		currentIndex = cards.currentIndex; 
		deck = new Card[cards.numLeft];
		numLeft = cards.numLeft;
		for (int i = 0; i <= currentIndex; i++) {
			deck[i] = cards.drawOne(); 
		}
		shuffle();
	}
	
	public void shuffle() {
		Random r = new Random();
		for (int i = 0; i <= currentIndex; i++) {
			int indexSwap = r.nextInt(numLeft);
			Card temp = deck[i];
			deck[i] = deck[indexSwap]; 
			deck[indexSwap] = temp;
		}
	}
	
	public Card drawOne() {
		if (numLeft == 0) {
			return null;
		} else {
			return draw(1).get(0);
		}
	}
	
	public ArrayList<Card> draw(int num) {
		ArrayList<Card> result = new ArrayList<>(); 
		int count = 0; 
		while (count < num && numLeft > 0) {
			result.add(deck[currentIndex]);
			currentIndex--;
			count++;
			numLeft--;
		}
		return result;
	}
	
	public int getNumLeft() {
		return numLeft; 
	}
	
	public void reset() {
		refill();
		shuffle();
	}
	
	public void refill() {
		currentIndex = deck.length - 1; 
		numLeft = deck.length;
	}
	
	@Override
	public String toString() {
		String res = "[";
		for (int i = 0; i <= currentIndex; i++) {
			res += deck[i] + ", "; 
		}
		res += "]"; 
		return res;
	}
	
	public static void main(String[] args) {
		Card card1 = new Card(1, Suit.CLUB);
		Card card2 = new Card(2, Suit.DIAMOND);
		Card card3 = new Card(3, Suit.SPADE);
		ArrayList<Card> testList = new ArrayList<>();
		testList.add(card1);
		testList.add(card2);
		testList.add(card3);
//		Deck test = new Deck(testList); 
//		System.out.println(test);
//		System.out.println(test.draw(4));
//		System.out.println(test);
	}
	
}
