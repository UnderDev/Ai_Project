package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import ie.gmit.sw.ai.traversers.RecursiveDFSTraversator;
import ie.gmit.sw.ai.maze.Maze;
import ie.gmit.sw.ai.maze.MazeGenerator;
import ie.gmit.sw.ai.traversers.*;


public class GameRunner implements KeyListener{
	private static final int MAZE_DIMENSION = 50;
	private static final int IMAGE_COUNT = 14;
	private GameView view;
	private int currentRow;
	private int currentCol;
	private ArrayList<Character> spiderNames = new ArrayList<Character>();
	private ArrayList<Monster> spiders = new ArrayList<Monster>();
	private Sprite[] sprites;

	private Maze[][] maze;
	private Maze goal;
	Player player;

	public GameRunner() throws Exception{
		MazeGenerator m = new MazeGenerator(MAZE_DIMENSION, MAZE_DIMENSION);				
		maze = m.getMaze();
		view = new GameView(maze);
		player = new Player("Spartan Warrior", "resources/spartan_1.png", "resources/spartan_2.png");
		init();
		sprites = getSprites();
		spiders = getSpiders();
		
		Random r = new Random();
		player.setWeapon(6);
		spiders.get(0).setAngerLevel(Math.round(r.nextDouble()*10));
		spiders.get(0).setHealth(Math.round(r.nextDouble()*100));
		//spiders.get(0).setHealth(12);
		System.out.println(spiders.get(0).getHealth());
		System.out.println(spiders.get(0).isAlive(spiders.get(0).getHealth()));
		view.setSprites(sprites);
		System.out.println(spiders.get(0).fight(player.getWeapon(), spiders.get(0).getAngerLevel()));
		System.out.println(spiders.get(0).isAlive(spiders.get(0).getHealth()));
		System.out.println(spiders.get(0).getHealth());
		placePlayer();
		
		
		
		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
		view.setPreferredSize(d);
		view.setMinimumSize(d);
		view.setMaximumSize(d);
		//view.setCurrentPosition(goal.getRow(), goal.getCol());

		JFrame f = new JFrame("GMIT - B.Sc. in Computing (Software Development)");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.addKeyListener(this);
		f.getContentPane().setLayout(new FlowLayout());
		f.add(view);
		f.setSize(1000,1000);
		f.setLocation(100,100);
		f.pack();
		f.setVisible(true);    

		Traversator t = new RecursiveDFSTraversator();
		t.traverse(maze, maze[0][0]);
		
	}
	
	private void init(){
		
		spiderNames.add('\u0036');
		spiderNames.add('\u0037');
		spiderNames.add('\u0038');
		spiderNames.add('\u0039');
		spiderNames.add('\u003A');
		spiderNames.add('\u003B');
		spiderNames.add('\u003C');
		spiderNames.add('\u003D');
	}

	private void placePlayer(){   	
		currentRow = (int) (MAZE_DIMENSION * Math.random());
		currentCol = (int) (MAZE_DIMENSION * Math.random());
		maze[currentRow][currentCol].setMapItem('5'); //A Spartan warrior is at index 5
		maze[currentRow][currentCol].setGoal(true);
		goal = maze[currentRow][currentCol];
		updateView(); 		
	}

	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
		player.setPlayerNode(maze[currentRow][currentCol]);
		System.out.println(player.getPlayerNode().toString());
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
			maze[row][col].setMapItem('5');//Hero Char	
			maze[currentRow][currentCol].setGoal(false);
			maze[row][col].setGoal(true);
			return true;
		}else{

			if(spiderNames.contains(maze[row][col].getMapItem())){
				System.out.println("SPIDER Encountered!");
			}			

			return false; //Can't move
		}
	}

	private Sprite[] getSprites() throws Exception{
		//Read in the images from the resources directory as sprites. Note that each
		//sprite will be referenced by its index in the array, e.g. a 3 implies a Bomb...
		//Ideally, the array should dynamically created from the images... 
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new Feature("Hedge", "resources/hedge.png");
		sprites[1] = new Feature("Sword", "resources/sword.png");
		sprites[2] = new Feature("Help", "resources/help.png");
		sprites[3] = new Feature("Bomb", "resources/bomb.png");
		sprites[4] = new Feature("Hydrogen Bomb", "resources/h_bomb.png");
		sprites[5] = new Player("Spartan Warrior", "resources/spartan_1.png", "resources/spartan_2.png");
		sprites[6] = new Monster("Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png");
		sprites[7] = new Monster("Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png");
		sprites[8] = new Monster("Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png");
		sprites[9] = new Monster("Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png");
		sprites[10] = new Monster("Grey Spider", "resources/grey_spider_1.png", "resources/grey_spider_2.png");
		sprites[11] = new Monster("Orange Spider", "resources/orange_spider_1.png", "resources/orange_spider_2.png");
		sprites[12] = new Monster("Red Spider", "resources/red_spider_1.png", "resources/red_spider_2.png");
		sprites[13] = new Monster("Yellow Spider", "resources/yellow_spider_1.png", "resources/yellow_spider_2.png");
		spiders.add((Monster) sprites[6]);
		return sprites;
	}
	
	private ArrayList<Monster> getSpiders() throws Exception {

		spiders.add(new Monster("Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png"));
		spiders.add(new Monster("Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png"));
		spiders.add(new Monster("Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png"));
		spiders.add(new Monster("Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png"));
		spiders.add(new Monster("Grey Spider", "resources/grey_spider_1.png", "resources/grey_spider_2.png"));
		spiders.add(new Monster("Orange Spider", "resources/orange_spider_1.png", "resources/orange_spider_2.png"));
		spiders.add(new Monster("Red Spider", "resources/red_spider_1.png", "resources/red_spider_2.png"));
		spiders.add(new Monster("Yellow Spider", "resources/yellow_spider_1.png", "resources/yellow_spider_2.png"));
		return spiders;
	}

	public static void main(String[] args) throws Exception{
		new GameRunner();
	}
}