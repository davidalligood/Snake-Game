import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Creates flies on the screen for the frog to eat.
 * @author David Alligood
 *
 */
public class Insect extends GamePiece {
	
	private int pointValue;

	/**
	 * Constructor for fly object on the screen.
	 * @param location the location of the fly on the screen
	 * @param size the size of the fly image
	 * @param image the image to display
	 */
	public Insect(Point location, Rectangle size, Image image) {
		super(location, size, image);
		pointValue = 100;
	}
	
	/**
	 * Returns points to add to the players score when an fly is eaten by the frog
	 * @return the point value of the fly.
	 */
	public int getPointValue(){
		return pointValue;
	}
}
