import java.awt.Graphics;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Constructs items to be displayed on the screen.
 * @author Lucie Sullivan
 *
 */
public abstract class ScreenItem {
	
    protected Point location;
    protected Rectangle size;
    
    /**
     * Set the location and size of the item.
     * @param location Placement on the screen.
     * @param i Size of the item.
     */
    public ScreenItem(Point location, Rectangle size) {
   	 super();
   	 this.location = location;
   	 this.size = size;
    }

    /**
     * Get the location of the item.
     * @return the location of the item
     */
    public Point getLocation() {
   	 return location;
    }

    /**
     * Change the location.
     * @param location New location of the item
     */
    public void setLocation(Point location) {
   	 this.location = location;
    }

    /**
     * Get the size.
     * @return The size of the item.
     */
    public Rectangle getSize() {
   	 return size;
    }

    /**
     * Change the size.
     * @param size New size of the item.
     */
    public void setSize(Rectangle size) {
   	 this.size = size;
    }
    
    /**
     * Drawing method for subclasses to implement.
     * @param g The graphics object.
     */
    abstract public void draw(Graphics g);
    
   	 
}