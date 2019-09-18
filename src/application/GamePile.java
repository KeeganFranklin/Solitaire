package application;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.*;

public interface GamePile {
	
	public void add(Card[] cards);
	
	public void display(Group root);
	
	public Card[] remove(GamePile dest); 
	
	public boolean canAdd(GamePile other); 
	
	public Card getTop();
}
