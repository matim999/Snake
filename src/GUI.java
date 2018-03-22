import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.lang.reflect.*;

//import muse.MouseComponent.MouseHandler;
//import muse.MouseComponent.MouseMotionHandler;

public class GUI extends JFrame{

	/**
	 * 
	 */
	private static JPanel gamePane;
	private static JLabel lblGgs;
    private static JLabel lblNewLabel;
	private static final long serialVersionUID = 1L;
	private static char direction = 'R';
	private static int delay = 300;
	private static boolean waitFlag = false;
	private static boolean start = false;
	private static boolean gameOver = false;
	public static int width = 10;
	public static int height = 14;
	public static int score = 0;
	public static int highscore = 0;
	private static JTextField textField;
	private static JTextField textField_1;
	private static Snake snake = new Snake();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public GUI() {
		try {
			FileInputStream in = new FileInputStream("hs.txt");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gamePane = new JPanel();
		setContentPane(gamePane);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setResizable(false);	
		int szerokosc = width*(Element.size+2)+7;
		int wysokosc = (height+1)*(Element.size+2)+32;
		setSize(szerokosc,wysokosc);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		snake.setBorder(new CompoundBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)), null), null));
		snake.setBackground(Color.DARK_GRAY);
		addKeyListener(new KeyHandle());
		//Systemout.println(this.getSize());
		
		textField = new JTextField();
		textField.setDisabledTextColor(Color.BLACK);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setEditable(false);
		textField.setEnabled(false);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setDisabledTextColor(Color.BLACK);
		textField_1.setAutoscrolls(false);
		textField_1.setSelectedTextColor(Color.DARK_GRAY);
		textField_1.setForeground(Color.RED);
		textField_1.setBackground(Color.LIGHT_GRAY);
		textField_1.setEnabled(false);
		textField_1.setColumns(10);
		textField_1.setHorizontalAlignment(JTextField.RIGHT);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1))
						.addComponent(snake, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(snake, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		getContentPane().setLayout(groupLayout);
		showScore();
		showHighScore();
		//initSnakeAndApple();
		
		Runnable r = () -> 
		{
			while(true)
			{
				////Systemout.println();
		         try
		         {  
		        	if(start)
		        	{
			        	if(!snake.moveObject(direction))
			        		stopAfterCollision();
			        	else
			        	{
				        	waitFlag = false;
					        Thread.sleep(delay);
					        snake.repaint();
			        	}
		        	}
		        	else{
		        		stopAfterCollision();
		        	}
		         }
		         catch (InterruptedException f)
		         {
		         }
			}
	     };
	     Thread t = new Thread(r);
		 t.start();
	}
	
	public static void adjustSpeed()
	{
		//Systemout.println("Adjust");
		delay -= 30;
	}
	
	public static void showScore()
	{
		textField.setText("Score: " + score);
	}
	
	public static void showHighScore()
	{
		byte c;
		try {
			FileInputStream in = new FileInputStream("hs.txt");
			highscore = (int)in.read();
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Systemout.println(highscore);
		textField_1.setText("Highcore: " + highscore);
	}
	
	private void stopAfterCollision()
	{
		gameOver = true;
		gamePane.remove(snake);
		if(start)
		{
			lblGgs = new JLabel("Game Over", SwingConstants.CENTER);
			lblGgs.setHorizontalTextPosition(SwingConstants.CENTER);
			lblGgs.setFont(new Font("Tahoma", Font.BOLD, 20));
			
			lblNewLabel = new JLabel("Press \"Space\" to play again");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		else
		{
			lblGgs = new JLabel("<html>Press W to move UP<br/>Press S to move DOWN<br/>Press A to move LEFT<br/>Press D to move RIGHT<br/></html>", SwingConstants.CENTER);
			lblGgs.setHorizontalTextPosition(SwingConstants.CENTER);
			lblGgs.setFont(new Font("Tahoma", Font.BOLD, 20));
			
			lblNewLabel = new JLabel("Press \"Space\" to Start");
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		if(score > highscore)
		{
			try {
				byte c = (byte)GUI.score;
				//Systemout.println(c);
				FileOutputStream out = new FileOutputStream("hs.txt");
				out.write(c);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		gamePane.add(lblGgs);
		gamePane.add(lblNewLabel);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_1))
						.addComponent(lblGgs, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(lblGgs, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		getContentPane().setLayout(groupLayout);
		gamePane.repaint();
		for (Thread t : Thread.getAllStackTraces().keySet()) 
		{
			if(t.getName().contains("Thread-"))
			{
				t.suspend();
			}				
		}
	}
	
	private static void initSnakeAndApple()
	{
		Point p = new Point(1+(Element.size+2)*(GUI.width/2), 1+(Element.size+2)*(GUI.height/2));
		for(int i=0; i<5; i++)
		{
			p.setX(p.getX()-(Element.size+2));
			SnakeElement quad = new SnakeElement(p);
			snake.add(quad);
		}
		snake.addApple();
	}
	
	private static void newGame()
	{
		direction = 'R';
		delay = 150;
		waitFlag = false;
		gameOver = false;
		score = 0;
		gamePane.add(snake);
		gamePane.remove(lblGgs);
		gamePane.remove(lblNewLabel);
		showScore();
		showHighScore();
		Snake.clearLists();
		initSnakeAndApple();
		snake.repaint();
		for (Thread t : Thread.getAllStackTraces().keySet()) 
		{
			if(t.getName().contains("Thread-"))
			{
				t.resume();
			}
		}
	}
	
	private class KeyHandle implements KeyListener
	{
	    public void keyTyped(KeyEvent e) {
	    }
	    public void keyReleased(KeyEvent e) {
	    }
	    public void keyPressed(KeyEvent e) 
	    {
	    	start = true;
	    	char c = Character.toLowerCase(e.getKeyChar());
	    	if(!waitFlag)
	    	{
		    	switch(c)
		    	{
		    		case 'w':
		    			if(direction == 'D' || direction == 'U')
		    			{
		    				break;
		    			}
		    			direction = 'U';
		    			waitFlag = true;
		    			break;
		    		case 'd':
		    			if(direction == 'L' || direction == 'R')
		    			{
		    				break;
		    			}
		    			direction = 'R';
		    			waitFlag = true;
		    			break;
		    		case 's':
		    			if(direction == 'U' || direction == 'D')
		    			{
		    				break;
		    			}
		    			direction = 'D';
		    			waitFlag = true;
		    			break;
		    		case 'a':
		    			if(direction == 'R' || direction == 'L')
		    			{
		    				break;
		    			}
		    			direction = 'L';
		    			waitFlag = true;
		    			break;
		    	}
	    	}
	    	if(gameOver)
	    	{
	    		if(c == ' ')
	    		{
	    			start = true;
	    			newGame();
	    		}
	    	}
	    }
	}
}
