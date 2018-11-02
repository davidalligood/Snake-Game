import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

/**
 * 
 * @author Lucie Sullivan
 * @Param Start Screen is the first screen a player sees 
 * @return Title of game, Start button, Difficulty levels
 */
public class StartScreen extends JFrame {
	
	JPanel startPanel = new JPanel(); // Main display panel
	 private JPanel difficultyPanel = new JPanel(new GridLayout(0, 1)); // Radio button panel
	 private JPanel buttonPanel = new JPanel(); // Button panel


	 ButtonGroup difficulty = new ButtonGroup(); // JRadioButton group for difficulty selection

	 JLabel title = new JLabel("Herbert the Frog", SwingConstants.CENTER);
	 
	 private final int WINDOW_WIDTH = 625;   // Window width
	 private final int WINDOW_HEIGHT = 625;  // Window height
	 
	// Strings for each difficulty setting
	 static String easyString = "Easy";
	 static String mediumString = "Medium";
	 static String hardString = "Hard";
	 String difficultyString = "Easy";
	 
	//Sound files
	 private File FrogStart = new File("frogIntro3.wav");
	 private Clip clip;
	
	 
	/**
	 * Constructor for startScreen, calls buildStartPanel.
	 */
	 public StartScreen(){
	  buildStartPanel();
	  
	  
	try{
		clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(FrogStart));
		clip.loop(1);
	}catch(Exception e){System.out.print("file not found");}
	 

	 }
	  
	 /**
	  * Builds panel to add to start screen gui.
	  */
	 public void buildStartPanel(){
	  //Set the title
	  setTitle("Herbert the Frog");
	  
	  
	  setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
	  // Sets panel background color
	  startPanel.setBackground(Color.GREEN);

	 
	  
	  // Center aligns startPanel
	  GridBagConstraints gbc = new GridBagConstraints();
	  startPanel.setLayout(new GridBagLayout());
	  
	  // Set title attributes
	  title.setFont (new Font("Arial", Font.BOLD, 38));
	  title.setForeground(Color.BLACK);
	  
	  
	  // Creates action listener
	  JButtonListener listener = new JButtonListener();
	  
	  // Creates JRadioButtons for difficulty levels
	  JRadioButton easy = new JRadioButton(easyString);
	  easy.setActionCommand(easyString);
	  easy.setHorizontalAlignment(AbstractButton.CENTER);
	  JRadioButton medium = new JRadioButton(mediumString);
	  medium.setActionCommand(mediumString);
	  medium.setHorizontalAlignment(AbstractButton.CENTER);
	  JRadioButton hard = new JRadioButton(hardString);
	  hard.setActionCommand(hardString);
	  hard.setHorizontalAlignment(AbstractButton.CENTER);
	  
	  // Creates JButton for game start
	  JButton startButton = new JButton("Start");
	  startButton.setActionCommand("Start");
	  startButton.addActionListener(listener);
	  buttonPanel.add(startButton); 
	  
	  // Creates group of JRadioButtons
	  difficulty.add(easy);
	  easy.setActionCommand("Easy");
	  easy.addActionListener(listener);
	  easy.setSelected(true);
	  difficulty.add(medium);
	  medium.setActionCommand("Medium");
	  medium.addActionListener(listener);
	  difficulty.add(hard);
	  hard.setActionCommand("Hard");
	  hard.addActionListener(listener);
	  
	  // Adds components to JFrame
	  difficultyPanel.add(easy);
	  difficultyPanel.add(medium);
	  difficultyPanel.add(hard);
	  difficultyPanel.add(buttonPanel);
	  startPanel.add(title, gbc);
	  
	  // Adds panels to JFrame
	  add(startPanel, BorderLayout.CENTER);
	  add(difficultyPanel, BorderLayout.SOUTH);
	  
	  
	  // Makes panel visible  
	  setVisible(true); 
	  
	  pack();
	  }
	 
	 /**
	  * Actionlistener for startScreen
	  * @author Lucie Sullivan
	  *
	  */
	 class JButtonListener implements ActionListener {
	  /**
	   * The actionPerformed method executes when the user clicks on the
	   * Calculate button.
	   * 
	   * @param e
	   *            The event object.
	   */
	       public void actionPerformed(ActionEvent e){ 
	        String actionSource = e.getActionCommand();
	        
	        // Passes in difficulty and starts game
	        if (actionSource.equals("Start") ){
	         //clip.stop();
	         Game gameWindow = new Game(difficultyString);
	        }
	        
	        // Sets difficulty based on which radiobutton is selected
	        if (actionSource.equals("Easy") | actionSource.equals("Medium") | actionSource.equals("Hard")){
	         difficultyString = actionSource;
	        }
	        
	     }
	 }




	 public static void main(String[] args){
	 StartScreen start = new StartScreen(); 
	 }
}

