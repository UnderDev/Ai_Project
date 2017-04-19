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
	private ArrayList<Monster> spiders = new ArrayList<Monster>();
	private ArrayList<MapItem> items = new ArrayList<MapItem>();
	private Sprite[] sprites;
	char item;

	private Maze[][] maze;
	Player player;

	public GameRunner() throws Exception{
		MazeGenerator m = new MazeGenerator(MAZE_DIMENSION, MAZE_DIMENSION);				
		maze = m.getMaze();
		view = new GameView(maze);
		player = new Player("Spartan Warrior", "resources/spartan_1.png", "resources/spartan_2.png");
		sprites = getSprites();
		spiders = getSpiders();
		items = getItems();
		setUpSpiders();


		view.setSprites(sprites);
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
	
	private void placePlayer(){   	
		currentRow = (int) (MAZE_DIMENSION * Math.random());
		currentCol = (int) (MAZE_DIMENSION * Math.random());
		maze[currentRow][currentCol].setMapItem('5'); //A Spartan warrior is at index 5
		maze[currentRow][currentCol].setGoal(true);
		//goal = maze[currentRow][currentCol];
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
			
			item =  maze[row][col].getMapItem();
			System.out.println(item);

			for(int i=0; i<spiders.size(); i++)
			{
				//System.out.println(spiders.get(i).getChar() + " " + maze[row][col].getMapItem());
				if(spiders.get(i).getChar() == item)
				{
					while(spiders.get(i).isAlive(spiders.get(i).getHealth()))
					{
						spiders.get(i).fight(player.getWeapon(), spiders.get(i).getAngerLevel());
						System.out.println(spiders.get(i).getName());
						System.out.println(spiders.get(i).getHealth());
					}
					System.out.println("Spider died");
					
				}
			}
				
				switch(item)
				{
					case '1':
						player.betterWeapon(5);
						System.out.println("Weapon: " + player.getWeapon());
						break;
					case '2':
						player.giveHealth(10);
						System.out.println("Health: " + player.getHealth());
						break;
					case '3':
						player.betterWeapon(10);
						System.out.println("Weapon: " + player.getWeapon());
						break;
					case '4':
						player.betterWeapon(20);
						System.out.println("Weapon: " + player.getWeapon());
					default:
						break;
				}
			
//				for(int j=0; j<items.size(); j++)
//				{
//					if(items.get(j).getChar() == maze[row][col].getMapItem())
//					{
//						if(items.get(j).getName() == "Sword")
//						{
//							player.betterWeapon(5);
//							System.out.println(player.getWeapon());
//						}
//						else if(items.get(j).getName() == "Help")
//						{
//							player.giveHealth(10);
//							System.out.println(player.getHealth());
//						}
//						else if(items.get(j).getName() == "Bomb")
//						{
//							player.betterWeapon(10);
//							System.out.println(player.getWeapon());
//						}
//					}
//				}

			return false; //Can't move
		}
	}

	private Sprite[] getSprites() throws Exception{
		//Read in the images from the resources directory as sprites. Note that each
		//sprite will be referenced by its index in the array, e.g. a 3 implies a Bomb...
		//Ideally, the array should dynamically created from the images... 
		Sprite[] sprites = new Sprite[IMAGE_COUNT];
		sprites[0] = new MapItem("Hedge", "resources/hedge.png");
		sprites[1] = new MapItem("Sword", "resources/sword.png");
		sprites[2] = new MapItem("Help", "resources/help.png");
		sprites[3] = new MapItem("Bomb", "resources/bomb.png");
		sprites[4] = new MapItem("Hydrogen Bomb", "resources/h_bomb.png");
		sprites[5] = new Player("Spartan Warrior", "resources/spartan_1.png", "resources/spartan_2.png");
		sprites[6] = new Monster("Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png");
		sprites[7] = new Monster("Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png");
		sprites[8] = new Monster("Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png");
		sprites[9] = new Monster("Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png");
		sprites[10] = new Monster("Grey Spider", "resources/grey_spider_1.png", "resources/grey_spider_2.png");
		sprites[11] = new Monster("Orange Spider", "resources/orange_spider_1.png", "resources/orange_spider_2.png");
		sprites[12] = new Monster("Red Spider", "resources/red_spider_1.png", "resources/red_spider_2.png");
		sprites[13] = new Monster("Yellow Spider", "resources/yellow_spider_1.png", "resources/yellow_spider_2.png");
		return sprites;
	}
	
	private ArrayList<Monster> getSpiders() throws Exception {

		spiders.add(new Monster('\u0036', "Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png"));
		spiders.add(new Monster('\u0037', "Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png"));
		spiders.add(new Monster('\u0038', "Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png"));
		spiders.add(new Monster('\u0039', "Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png"));
		spiders.add(new Monster('\u003A', "Grey Spider", "resources/grey_spider_1.png", "resources/grey_spider_2.png"));
		spiders.add(new Monster('\u003B', "Orange Spider", "resources/orange_spider_1.png", "resources/orange_spider_2.png"));
		spiders.add(new Monster('\u003C', "Red Spider", "resources/red_spider_1.png", "resources/red_spider_2.png"));
		spiders.add(new Monster('\u003D', "Yellow Spider", "resources/yellow_spider_1.png", "resources/yellow_spider_2.png"));
		return spiders;
	}
	
	private void setUpSpiders()
	{
		Random r = new Random();
		
		for(int i=0; i<spiders.size(); i++)
		{
			spiders.get(i).setAngerLevel(Math.round(r.nextDouble()*10));
			spiders.get(i).setHealth(Math.round(r.nextDouble()*100));
		}
	
	}
	
	private ArrayList<MapItem> getItems() throws Exception
	{
		items.add(new MapItem('\u0031', "Sword", "resources/sword.png"));
		items.add(new MapItem('\u0032', "Help", "resources/help.png"));
		items.add(new MapItem('\u0033', "Bomb", "resources/bomb.png"));
		items.add(new MapItem('\u0034', "Hydrogen Bomb", "resources/bomb.png"));
		
		return items;
	}

	public static void main(String[] args) throws Exception{
		new GameRunner();
	}
}