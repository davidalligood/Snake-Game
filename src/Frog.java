import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;



//import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

import java.util.ArrayList;
import java.util.LinkedList;


public class Frog extends GamePiece{
	private int direction; //Direction of frog

	/**
	 * Constructor for frog object
	 * @param point
	 * @param size
	 * @param direction
	 * @param i
	 */
	public Frog(Point point, Rectangle size, int direction, Image i){
		super(point, size, i);
		this.direction = direction;
	}
	
	
	 /**
     * Get the direction of the frog's face.
     * @return the direction
     */
    public int getDirection() {
   	 return direction;
    }
    
    /**
     * Change the direction of the frog's face.
     * @param direction
     *        	the direction to set
     */
    public void setDirection(int direction) {
    	
    	if(direction == 0){
    			this.moveY =  -25;
    			this.moveX = 0;
    	}
    	if(direction == 90){
    		this.moveY =  0;
			this.moveX = 25;
    	}
    	if(direction == 180){
    		this.moveY =  25;
			this.moveX = 0;
    	}
    	if(direction == 270){
    		this.moveY =  0;
			this.moveX = -25;
    	}
    	
   	 this.direction = direction;
    }
}
