import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JFrame;


public class Game extends JFrame {
	/**
	 * The window contains the game Screen.
	 * @author Lucie Sullivan
	 */
	public Game(String difficulty) {
		setTitle("Herbert the Frog");
		Screen screen = new Screen(difficulty);
		//StartScreen startGUI = new StartScreen();
		add(screen);
		//add(startGUI);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
}
