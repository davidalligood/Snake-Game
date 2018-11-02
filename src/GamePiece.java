import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.geom.AffineTransform;

//import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * Abstract superclass to handle creation of game pieces on the game board.
 *
 * @author David Alligood
 *
 */
public abstract class GamePiece extends ScreenItem {

    protected Image image; // Image attribute
    protected int moveX; // Change in X for gamePiece movements
    protected int moveY = -25; // Change in Y for gamePiece movements

    /**
     * Super constructor for Frog and Insect objects.
     */
    public GamePiece(Point location, Rectangle size, Image image) {
   	 super(location, size);
   	 this.image = image;

    }

    /**
     * Return true if game pieces collide.
     *
     * @param otherObj
     *        	The other moving object.
     * @return True if collision; else False.
     */
    public boolean collision(GamePiece otherObj) {
   	 Rectangle other = otherObj.getSize();
   	 other.setLocation(otherObj.getLocation());
   	 this.getSize().setLocation(this.getLocation());
   	 if (other.intersects(this.getSize())) {
   		 return true;
   	 } else {
   		 return false;
   	 }
    }

    /**
     * Draws GamePieces onto screen
     */
    public void draw(Graphics g){
   	 
   	 Graphics2D g2 = (Graphics2D) g;
 
   	 if (this instanceof Frog){
   		 Frog frog = (Frog) this;
   	 
   	 
   	 //To change the direction of the frog's face.
   	 AffineTransform trans = new AffineTransform();
   	 trans.translate(location.x, location.y);
   	 
   	 //Scale the drawing once we have image.
   	 trans.scale(0.8, 0.8);
   	 
   	 //Add to Frog class
   	 trans.rotate(Math.toRadians(frog.getDirection()), image.getWidth(null)/2, image.getHeight(null)/2);
   	 
   	 g2.drawImage(image, trans, null);
   	 }
   	 
   	 else if (this instanceof Insect){
 		 Insect fly = (Insect) this;
 	 
 	 
 	 //To scale up images
 	 AffineTransform trans = new AffineTransform();
 	 trans.translate(location.x, location.y);
 	 //Scale the drawing once we have image.
 	 trans.scale(1, 1);

 	 g2.drawImage(image, trans, null);
 	 }
   	 
   	else if (this instanceof Bird){
		 Bird bird = (Bird) this;
	 
	 
	 //To scale up images
	 AffineTransform trans = new AffineTransform();
	 trans.translate(location.x, location.y);
	 //Scale the drawing once we have image.
	 trans.scale(.8, .8);

	//Add to Frog class
   	 trans.rotate(Math.toRadians(bird.getDirection()), image.getWidth(null)/2, image.getHeight(null)/2);
	 
	 g2.drawImage(image, trans, null);
	 }
   	 
   	 else{
   		 g2.drawImage(image,  location.x, location.y, size.width, size.height, null);
   		 }
   	 
    }
    
   
    /**
     * Set the velocity of the game piece.
     *
     * @param xChange
     *        	The change in the x position.
     * @param yChange
     *        	The change in the y position.
     */
    public void setVelocity(int xChange, int yChange) {
   	 moveX = xChange;
   	 moveY = yChange;

    }

    /**
     * Move the object.
     */
    public void move() {
   	 location.x += moveX;
   	 location.y += moveY;
    }

    /**
     * Get the change in the x position of the game piece.
     *
     * @return the x-coordinate change.
     */
    public int getMoveX() {
   	 return moveX;
    }

    /**
     * Get the change in the y position of the game piece.
     *
     * @return the y-coordinate change.
     */
    public int getMoveY() {
   	 return moveY;
    }

    /**
     * Returns the image for drawing purposes.
     *
     * @return the image of the game piece.
     */
    public Image getImage() {
   	 return image;
    }

    /**
     * Set the image associated with the Game Piece.
     *
     * @param theImage
     *        	The image to set for the game piece.
     */
    public void setImage(Image theImage) {
   	 image = theImage;
    }
 
}

