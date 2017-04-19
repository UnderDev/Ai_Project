package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import ie.gmit.sw.ai.traversers.RecursiveDFSTraversator;
import ie.gmit.sw.ai.traversers.Traversator;
import ie.gmit.sw.ai.maze.Maze;
import ie.gmit.sw.ai.maze.MazeGenerator;



public class GameRunner implements KeyListener{
	private static final int MAZE_DIMENSION = 100;
	private static final int IMAGE_COUNT = 14;
	private GameView view;
	private int currentRow;
	private int currentCol;

	private Player player;
	private Maze goal;

	private Sprite[] sprites;
	char item;

	private Maze[][] maze;

	public GameRunner() throws Exception{
		MazeGenerator m = new MazeGenerator(MAZE_DIMENSION, MAZE_DIMENSION);				
		maze = m.getMaze();
		view = new GameView(maze);

		player = new Player();

		sprites = getSprites();
		view.setSprites(sprites);
		placePlayer();

		//view.toggleZoom(); //testing only ******* REMOVE	
		view.setSprites(sprites);

		placePlayer();			

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

		//Traversator t = new RecursiveDFSTraversator();
		//t.traverse(maze, maze[0][0]);
	}


	private void placePlayer(){   	
		currentRow = (int) (MAZE_DIMENSION * Math.random());
		currentCol = (int) (MAZE_DIMENSION * Math.random());
		maze[currentRow][currentCol].setMapItem('5'); //A Spartan warrior is at index 5
		maze[currentRow][currentCol].setGoal(true);

		goal = maze[currentRow][currentCol];
		updateView();		
	}

//	private void placeMonsters(){	
//		//for (int i = 0; i < 2; i++) {
//		currentRow = (int) (MAZE_DIMENSION * Math.random());
//		currentCol = (int) (MAZE_DIMENSION * Math.random());
//		maze[currentRow][currentCol].setMapItem('\u003D');
//
//		Traversator t = new RecursiveDFSTraversator();
//		t.traverse(maze, maze[currentRow][currentCol]);
//		//}		
//	}

	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
		player.setPlayerNode(maze[currentRow][currentCol]);

		//System.out.println(player.getPlayerNode().toString());
		//goal = player.getPlayerNode();
	}


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


	private boolean isValidMove(int row, int col){
		//Move The Character to the space passed in if valid
		if (row <= maze.length - 1 && col <= maze[row].length - 1 && maze[row][col].getMapItem() == ' '){
			maze[currentRow][currentCol].setMapItem('\u0020');//Space
			maze[currentRow][currentCol].setGoal(false);

			maze[row][col].setMapItem('5');//Hero Char	
			maze[row][col].setGoal(true);

			return true;
		}else{			
			item =  maze[row][col].getMapItem();

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

			return false; //Can't move
		}
	}

	private Sprite[] getSprites() throws Exception{
		//Read in the images from the resources directory as sprites. Note that each
		//sprite will be referenced by its index in the array, e.g. a 3 implies a Bomb...
		//Ideally, the array should dynamically created from the images... 
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

	public static void main(String[] args) throws Exception{
		new GameRunner();
	}
}