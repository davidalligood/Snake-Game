import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.sound.sampled.Clip;
import javax.swing.*;

import java.io.*;

import javax.sound.sampled.AudioSystem;

/**
 * @author Lucie Sullivan
 * @Param Makes a JPanel that makes the display for screen
 * @return A Screen which the game displays on
 *
 */

public class Screen extends JPanel implements KeyListener {
	
	//window dimensions
	private final int WINDOW_WIDTH = 625;
	private final int WINDOW_HEIGHT = 625;
	
	//score variables
	private int score = 0;
	private int pointValue; // value to add to the score depend on item eaten
	private JPanel scorePanel = new JPanel(); // Score board
	
	//screen labels
	private JLabel scoreLabel = new JLabel("Score: 0");
	private JLabel pauseTitle = new JLabel("Paused", SwingConstants.CENTER); 
	private JLabel continueGame = new JLabel("<html><center><br> Press Y to Replay </center></html>", SwingConstants.CENTER);
	private JLabel life = new JLabel("        Lives: 3");
	
	//location variable and size
	private Rectangle gameRectangle = new Rectangle(25,25); // size for images
	
	//Frog variables
	private Frog playerFrog; //the head
	private Bird evilBird = new Bird(new Point(625,625), new Rectangle(30,30), 0, bird.getImage());
	private LinkedList<Frog> frogPieces; //contains Frog and tadpoles
	private int tempDirection;
	private int frogDirection;
	private Point tempLocation;
	
	//Life variables
	private int lives = 3; 
	
	//user's choice of difficult level
	private String difficulty;
	private javax.swing.Timer timer = new javax.swing.Timer(200, new TimerListener());
	
	// Clip to hold and play sound file
	private Clip clip;
	
	//Sound files
	private File FrogStart = new File("frogIntro3.wav");
	private File SnakeEat = new File("snakeEat.wav");
	private File SnakeLose = new File("snakeLose.wav");
	private File SnakeOver = new File("snakeTheme.wav");
	
	//Image files
	public static ImageIcon background = new ImageIcon("Lilypad.png");
	public static ImageIcon titleImg = new ImageIcon("Frog.png");
	public static ImageIcon frog = new ImageIcon("Frog.png");
	public static ImageIcon tadpole = new ImageIcon("Tadpole.png");
	public static ImageIcon bird = new ImageIcon("Bird.png");
	public static ImageIcon fly = new ImageIcon("Fly.png");
	public static ImageIcon butterfly = new ImageIcon("Butterfly.png");
	public static ImageIcon dragonfly = new ImageIcon("Dragonfly.png");
	public static ImageIcon worm = new ImageIcon("Worm.png");
	public static ImageIcon grasshopper = new ImageIcon("Grasshopper.png");
	public static ImageIcon mosquitoe = new ImageIcon("Mosquitoe.png");
	public static ImageIcon snake = new ImageIcon("Rattle.gif");
	private ImageIcon[] insects = {fly, butterfly, dragonfly, worm, grasshopper, mosquitoe};
	Insect gameInsect = new Insect(new Point(0, 0), new Rectangle(20,20), fly.getImage());
	Barrier block = new Barrier(new Point(0, 0), new Rectangle(50,50), snake.getImage());

	//Handles the speed of the frog
	public int timer_delay;
	public int boardFriction;
	public int minimumDelay; 
	
	//Game play screen controls
	private boolean pause = false; 
	private boolean gameStart = true;
	
	//Controls for bird event
	boolean birdOut = false;
	boolean eatFrog = false;
	
	/**Constructs a new instance of the game screen.
     * @param difficulty the difficulty of the level
     */
	public Screen(String difficulty){
		this.difficulty = difficulty;	
		
		// Set size
		setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		
		// Set layout
		setLayout(new BorderLayout());
		
		// Allows JFrame to be focused
		setFocusable(true);
		
		//Build the panel
		buildPanel();
				
		// Sets background color
		setBackground(Color.BLUE);
				
		// Makes panel visible  
		setVisible(true); 
		
		//Start a new game
		startGame();
		
		
	}
	
	/**
	 * Build the panel the game will be displayed on.
	 */
	public void buildPanel(){
		
		// Adds keyListener
		this.addKeyListener(this);

		
		// Set score JLabel attributes
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
		scoreLabel.setForeground(Color.BLACK);
		scoreLabel.setHorizontalAlignment(0);
		scorePanel.add(scoreLabel);
		life.setFont(new Font("Arial", Font.BOLD, 18));
		life.setForeground(Color.BLACK);
		life.setHorizontalAlignment(0);
		scorePanel.add(life);
		add(scorePanel, BorderLayout.SOUTH);
		scorePanel.setLayout(new GridBagLayout());
		
		// Set pause JLabel attributes
		pauseTitle.setFont (new Font("Arial", Font.BOLD, 128));
		pauseTitle.setForeground(Color.WHITE);
		pauseTitle.setVisible(false);
		add(pauseTitle, BorderLayout.NORTH);
		
		// Set restart prompt JLabel attributes
		continueGame.setFont (new Font("Arial", Font.BOLD, 64));
		continueGame.setForeground(Color.WHITE);
		continueGame.setVisible(false);
		add (continueGame, BorderLayout.CENTER);
		
			
		//Hides score until game starts
		scoreLabel.setVisible(false);
		
		//Create new frog list
		frogPieces = new LinkedList<Frog>();

	}
	
	/**
	 * Creates an instance of frog and adds it to the list of frogPieces.
	 */
	public void addFrog(){
		int width = WINDOW_WIDTH/2 - 12;
		int height = WINDOW_HEIGHT/2 - 12;
		
		playerFrog = new Frog(new Point(width,height), new Rectangle(gameRectangle), 0, frog.getImage());
		frogPieces.add(playerFrog);
	}
	
	/**
	 * Adds a tadpole piece if the frog has eaten the insect 
	 */
	public void addTadpoles(){
		Point tempPoint = frogPieces.get(frogPieces.size() - 1).getLocation();
		int tempDirection = frogPieces.get(frogPieces.size() - 1).getDirection();
		int offsetX = 0;
		int offsetY = 0;

		switch( tempDirection ) { 
		case 0:
			offsetX = 0;
			offsetY = 25;
			break;
		
		case 90: 
			offsetX = -25;
			offsetY = 0;
			break;	
			
		case 180: 
			offsetX = 0;
			offsetY = -25;
			break;
			
		case 270: 
			offsetX = 25;
			offsetY = 0;
			break;
		}
		
		frogPieces.add(new Frog(new Point((int)tempPoint.getX() + offsetX, (int)tempPoint.getY() + offsetY), new Rectangle(gameRectangle), 0, tadpole.getImage()));
		frogPieces.get(frogPieces.size()-1).setDirection(tempDirection);
		
		if (frogPieces.size() > 2){
			frogPieces.get(frogPieces.size() - 2).setImage(tadpole.getImage());
		}
	}
		
		/**
		 * Genebirdes a random insect to be displayed on the screen. 
		 * @return ImageIcon of insect 
		 */
		public ImageIcon genRandomInsect(){
			ImageIcon random = (insects[new Random().nextInt(insects.length)]);
			if (random.equals(fly)){
				pointValue = 100;
			}
			if (random.equals(mosquitoe)){
				pointValue = 150;
			}
			if (random.equals(butterfly)){
				pointValue = 200;
			}
			if (random.equals(dragonfly)){
				pointValue = 250;
			}
			if (random.equals(grasshopper)){
				pointValue = 300;
			}
			if (random.equals(worm)){
				pointValue = 350;
			}
			return random;
		}
		
		/**
		 *Creates a insect object and random place on the screen for the insect to appear. 
		 */
		public void addInsect(){
			  //genebirde random location to place the Insect. 
			  int width = WINDOW_WIDTH - 25;
			  int height = WINDOW_HEIGHT - 25;
			  Random randHeight = new Random();
			  Random randWidth = new Random();
			  
			  int randomHeight = ((randHeight.nextInt(height) * 25) % 600) + 25;
			  int randomWidth = ((randWidth.nextInt(width) * 25) % 600);
			  ImageIcon genInsect = genRandomInsect();
			  gameInsect.setImage(genInsect.getImage());
			  gameInsect.setLocation(new Point(randomWidth, randomHeight));
		}
		
		public void addBarrier(){
			int width = WINDOW_WIDTH - 25;
			int height = WINDOW_HEIGHT - 25;
			Random randHeight = new Random();
			Random randWidth = new Random();
			int randomHeight = ((randHeight.nextInt(height) * 25) % 600) + 25;
			int randomWidth = ((randWidth.nextInt(width) * 25) % 600);
			Point location = new Point(randomHeight, randomWidth);
			for (Frog obj : frogPieces) {
				if (location == obj.getLocation()) {
					location.x = Math.abs(location.x - width);
					location.y = Math.abs(location.y - height);
				}
			}
			block.setLocation(new Point(randomHeight, randomWidth));
		}
	
		/**
		 * Controls direction based on keys pressed, also pauses game 
		 */
		public void keyPressed(KeyEvent event) {
			   int keyCode = event.getKeyCode();
			    switch( keyCode ) { 
			        case KeyEvent.VK_UP:
			        		frogDirection = 0;
			            break;
			            
			        case KeyEvent.VK_RIGHT:
			        		frogDirection = 90;
			            break;
			            
			        case KeyEvent.VK_DOWN:
			        		frogDirection = 180;
			            break;
			            
			        case KeyEvent.VK_LEFT:
			        		frogDirection = 270;
			            break;	   
			            
			        case KeyEvent.VK_1:
			        	if (!gameStart){
		        		difficulty = "Easy";
			        	}
		            break;
		            
			        case KeyEvent.VK_2:
			        	if (!gameStart){
		        		difficulty = "Medium";
			        	}
		            break;
		            
			        case KeyEvent.VK_3:
			        	if (!gameStart){
			        	difficulty = "Hard";
			        	}
		            break;	
		            
			        case KeyEvent.VK_P:
			        	if (gameStart){
			        		pauseGame();
			        	}
			        	break;
			        	
			        case KeyEvent.VK_Y:
			        	if (!gameStart){
			        		lives = 3;
			        		startGame();
			        	}			        	
			        	break;    				     
			     }
		}
		
		/**
		 * The game timer.
		 * @return the timer
		 */
		public javax.swing.Timer getTimer() {
		
			return timer;
		}

		/**
		 * The timer that controls the speed of the game. 
		 * @param timer the timer to set
		 */
		public void setTimer(javax.swing.Timer timer) {
			this.timer = timer;
		}
		
		/**
		 * 
		 * Handles the action events such as reseting the game, detecting collisions and keeping track of the score.
		 *
		 */
		private class TimerListener implements ActionListener {
			
			public void actionPerformed(ActionEvent arg0) {
				if (!(playerFrog.getDirection() == frogDirection | playerFrog.getDirection() == frogDirection + 180 | playerFrog.getDirection() == frogDirection - 180)){
					playerFrog.setDirection(frogDirection);
				}
				
				for(Frog obj : frogPieces){
					if (playerFrog.collision(obj) && !(obj == playerFrog)){
	                   
	                    kill();
						resetGame();
					}
					else if(evilBird.collision(obj)){
						eatFrog = true;
					}	
				}
				
				if (playerFrog.collision(block)){
					kill();
					resetGame();
				}
				tempLocation = playerFrog.getLocation();
				if (tempLocation.getX() < 0 | tempLocation.getY() < 25 | tempLocation.getX() > 600 | tempLocation.getY() > 600){
					
					kill();
					resetGame();
				}
				else if(playerFrog.collision(gameInsect)){
					incrementScore();
					addInsect();
					addTadpoles();
					
					if (boardFriction > minimumDelay){
						boardFriction -= 5;
						timer.setDelay(boardFriction);
					}
					
					if (boardFriction % 25 == 0 & !(difficulty.equals("Easy"))){
						evilBird.setLocation(new Point(0,0));
		                birdOut = true;  
					}
					moveFrog();
					
				}
				
				
				else{
					
					moveFrog();
					
					if(birdOut){
						evilBird.chaseFrog(frogPieces.get(frogPieces.size()/2).getLocation());
						evilBird.move();
					}
				}
				
				if (birdOut){

					if (evilBird.collision(playerFrog)){
					addTadpoles();
					moveFrog();
					incrementScore();
					pointValue = 500;
					birdOut = false;
					evilBird.setLocation(new Point(625,625));

					}

					}
				if (eatFrog){
					frogPieces.remove(frogPieces.size() - 1);
					eatFrog = false;
					birdOut = false;
					evilBird.setLocation(new Point(625,625));
				}
				repaint();
				}
			
		}
		
		/**
		 * Responsible for painting objects on the screen and setting the background image. 
		 */
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			g.drawImage(background.getImage(), 0, 0, null);
				
			if(gameStart == true){
				gameInsect.draw(g);
				if(birdOut){
					evilBird.draw(g);
					}
				 if (block.getTimeToLive() == 0){
					    block.setTimeToLive(100);
					    addBarrier();
					    block.draw(g);;
					   }
					   else{
					    block.setTimeToLive(block.getTimeToLive()-1);
					    block.draw(g);
			   }
				for (Frog obj : frogPieces) {
					obj.draw(g);
				}
				
			}
			
		}
		

		/**
		 * Moves the Frog and the tadpoles parts of the frog.
		 */
		public void moveFrog(){
			for(Frog obj : frogPieces){
				obj.move();
			}
			
			for (int i = frogPieces.size() - 1 ; i > 0 ; i --) { 
				if(!(frogPieces.get(i-1).getDirection() == frogPieces.get(i).getDirection())){
					tempDirection = frogPieces.get(i - 1).getDirection();
					frogPieces.get(i).setDirection(tempDirection);
				}
			}
			
		}
		
		/**
		 * Starts the game, adds the frog and insects to the screen, gets difficulty starts the timer.
		 */
		public void startGame(){
			scoreLabel.setVisible(true);
			gameStart = true;
			continueGame.setVisible(false);
			addFrog();
			addInsect();
			
			switch( difficulty ) { 
			case "Easy":
				timer_delay = 200;
				minimumDelay = 60;
				break;
				
			case "Medium":
				timer_delay = 150;
				minimumDelay = 45;
				break;
				
			case "Hard":
				timer_delay = 100;
				minimumDelay = 30;
				break;
			}
			boardFriction = timer_delay;
			// Starts timer
			
			timer.setDelay(timer_delay);
			
			timer.start();
			//playSound(FrogStart);
		
		}
		

		/**
		 * Clears the board of frog and resets the score, stops the timer. 
		 */
		public void resetGame(){
			frogPieces.clear();
			if (lives <= 0){
				timer_delay = 200;
				resetScore();
				lives=3;
				life.setText("        Lives: " + Integer.toString(lives));
				continueGame.setVisible(true);
				gameStart = false;
	
				timer.stop();
			}
			else{
				startGame();
			}
				
		}
		
		/**
		 * Pauses the game, stops the timer is "P" is pressed during gameplay. 
		 */
		public void pauseGame(){
			if (pause == false){
				timer.stop();
				pauseTitle.setVisible(true);
				pause = true;
			}
			else{
				timer.start();
				pauseTitle.setVisible(false);
				pause = false;
			}
			
			
		}
		

		/**
		 * Increments the score if a Insect is eaten. 
		 */
		public void incrementScore(){
			score+= pointValue;
			scoreLabel.setText("Score: " + Integer.toString(score));
		}
		
		/**
		 * Resets the score back to zero. 
		 */
		public void resetScore(){
			score = 0;
			scoreLabel.setText("Score: " + Integer.toString(score));
			
		}
		
		/**
		 * Adjusts the number of lives when the player dies.
		 */
		public void kill(){
			lives--;
			life.setText("        Lives: " + Integer.toString(lives));
		}
		
		/**
		 * Unused key method.
		 */
		public void keyReleased(KeyEvent arg0) {
			
		}
		
		/**
		 * Unused key method.
		 */
		public void keyTyped(KeyEvent arg0) {
		
		}	
		
		public void playSound(File Sound){
			
			try{
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(Sound));
				clip.start();
			}catch(Exception e){System.out.print("file not found");}
		
		

			
		}
}
