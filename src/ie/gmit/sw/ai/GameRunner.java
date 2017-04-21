package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.*;

import ie.gmit.sw.ai.maze.Maze;
import ie.gmit.sw.ai.maze.MazeGenerator;
import ie.gmit.sw.ai.maze.MazeGeneratorFactory;

/*
 * Game Runner Is used as the starting point of the game, 
 * it calls all necessary methods to generate the maze, monsters and player etc
 * Implements Game Runner
 */

public class GameRunner implements KeyListener{
	private static final int MAZE_DIMENSION = 50;
	private static final int IMAGE_COUNT = 14;
	private GameView view;
	private int currentRow;
	private int currentCol;

	private Player player;
	private ThreadPoolExecutor executor;

	private Sprite[] sprites;
	private Maze[][] maze;
	private Monster monster;

	public GameRunner() throws Exception{
		MazeGeneratorFactory factory = MazeGeneratorFactory.getInstance();
		MazeGenerator m = factory.getMazeGenerator(MazeGenerator.GeneratorAlgorithm.BinaryTree, MAZE_DIMENSION, MAZE_DIMENSION);

		maze = m.getMaze();
		view = new GameView(maze);

		player = new Player();

		sprites = getSprites();
		view.setSprites(sprites);
		placePlayer();
		startMonsters();

		view.toggleZoom();

		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
		view.setPreferredSize(d);
		view.setMinimumSize(d);
		view.setMaximumSize(d);

		JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(this);
		f.getContentPane().setLayout(new FlowLayout());
		f.add(view);
		f.setSize(1000,1000);
		f.setLocation(100,100);
		f.pack();
		f.setVisible(true);   
		updateView();	
	}

	//This method creates / starts the Monster objects in a new thread  
	private void startMonsters() {
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
		Random r = new Random();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				char ch = maze[row][col].getMapItem(); //Index 0 is a hedge

				if(ch > '5'){
					if(ch <= '7')
						monster = new Monster(Math.round(r.nextDouble()*50), Math.round(r.nextDouble()*100), ch, row, col, maze, "bfs", player, "nn");
					else if(ch <= '9')
						monster = new Monster(Math.round(r.nextDouble()*50), Math.round(r.nextDouble()*50), ch, row, col, maze, "dfs", player, "fuzzy");
					else
						monster = new Monster(Math.round(r.nextDouble()*50), Math.round(r.nextDouble()*100), ch, row, col, maze, "aStar", player, "fuzzy");
					executor.execute(monster);
				}			
			}					
		}
	}

	/*This Method creates the a DEEP copy of the Object passed in
	Needed to give each Monster its own Maze to traverse*/
	public static Object copy(Object orig) {
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(
					new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return obj;
	}

	//Places the player in the maze at a random location
	private void placePlayer(){   	
		currentRow = (int) (MAZE_DIMENSION * Math.random());
		currentCol = (int) (MAZE_DIMENSION * Math.random());
		maze[currentRow][currentCol].setMapItem('5'); //A Spartan warrior is at index 5
		maze[currentRow][currentCol].setGoal(true);
		System.out.println("Play first pos  " + maze[currentRow][currentCol]);
		updateView();		
	}

	//Updates the current View
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
		player.setPlayerNode(maze[currentRow][currentCol]);
	}

	// Get the key Down Events for moving: up, down, left right and zoom
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
			if (isValidMove(currentRow, currentCol + 1)) currentCol++;   		
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
			if (isValidMove(currentRow, currentCol - 1)) currentCol--;	
		}else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
			if (isValidMove(currentRow - 1, currentCol)) currentRow--;
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
			if (isValidMove(currentRow + 1, currentCol)) currentRow++;        	  	
		}else if (e.getKeyCode() == KeyEvent.VK_Z){
			view.toggleZoom();
		}else{
			return;
		}

		updateView();       
	}
	public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore

	//Check to see if the person can move to the passed in Row/Col
	private boolean isValidMove(int row, int col){
		//Move The Character to the space passed in if valid
		if (row <= maze.length - 1 && col <= maze[row].length - 1 && maze[row][col].getMapItem() == ' '){
			maze[currentRow][currentCol].setMapItem('\u0020');//Space
			maze[currentRow][currentCol].setGoal(false);

			maze[row][col].setMapItem('5');//Hero Char	
			maze[row][col].setGoal(true);
			System.out.println("Play new pos  " + maze[row][col]);

			return true;
		}else{			
			char item =  maze[row][col].getMapItem();
			//Pick up the Item if valid
			checkItemFound(item, row, col);			
			return false; //Can't move
		}
	}

	//Checks to see if an item was found by the Player
	private void checkItemFound(char item, int row, int col) {
		switch(item)
		{
		case '1':
			player.betterWeapon(5);
			maze[row][col].setMapItem('0');
			System.out.println("Weapon: " + player.getWeapon());
			break;
		case '2':
			player.giveHealth(10);
			maze[row][col].setMapItem('0');
			System.out.println("Health: " + player.getHealth());
			break;
		case '3':
			player.betterWeapon(10);
			maze[row][col].setMapItem('0');
			System.out.println("Weapon: " + player.getWeapon());
			break;
		case '4':
			player.betterWeapon(20);
			maze[row][col].setMapItem('0');
			System.out.println("Weapon: " + player.getWeapon());
		default:
			break;
		}
	}

	//Create the Sprite Array 
	private Sprite[] getSprites() throws Exception{
		//Read in the images from the resources directory as sprites. Note that each
		//sprite will be referenced by its index in the array, e.g. a 3 implies a Bomb... 
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new Sprite("Hedge", "resources/hedge.png");
		sprites[1] = new Sprite("Sword", "resources/sword.png");
		sprites[2] = new Sprite("Help", "resources/help.png");
		sprites[3] = new Sprite("Bomb", "resources/bomb.png");
		sprites[4] = new Sprite("Hydrogen Bomb", "resources/h_bomb.png");
		sprites[5] = new Sprite("Spartan Warrior", "resources/spartan_1.png", "resources/spartan_2.png");
		sprites[6] = new Sprite("Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png");
		sprites[7] = new Sprite("Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png");
		sprites[8] = new Sprite("Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png");
		sprites[9] = new Sprite("Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png");
		sprites[10] = new Sprite("Grey Spider", "resources/grey_spider_1.png", "resources/grey_spider_2.png");
		sprites[11] = new Sprite("Orange Spider", "resources/orange_spider_1.png", "resources/orange_spider_2.png");
		sprites[12] = new Sprite("Red Spider", "resources/red_spider_1.png", "resources/red_spider_2.png");
		sprites[13] = new Sprite("Yellow Spider", "resources/yellow_spider_1.png", "resources/yellow_spider_2.png");
		return sprites;
	}

	//Main Class that calls GameRunner()
	public static void main(String[] args) throws Exception{
		new GameRunner();
	}
}