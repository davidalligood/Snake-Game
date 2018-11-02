import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;


public class Barrier extends GamePiece {
	
	private int timeToLive;

	public Barrier(Point location, Rectangle size, Image image) {
		super(location, size, image);
		timeToLive = 10;
		
	}
	
	public int getTimeToLive(){
		return timeToLive;
	}
	
	public void setTimeToLive(int newTime){
		timeToLive = newTime;
	}

}
