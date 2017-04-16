package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class GameRunner implements KeyListener{
	private static final int MAZE_DIMENSION = 100;
	private static final int IMAGE_COUNT = 14;
	private GameView view;
	private Maze model;
	private int currentRow;
	private int currentCol;
	
	public GameRunner() throws Exception{
		model = new Maze(MAZE_DIMENSION);
    	view = new GameView(model);
    	Sprite[] sprites = getSprites();
    	view.setSprites(sprites);
    	
    	//FuzzyMonster[] fMonster = getFuzzyMonsters();
    	//fMonster[1].getName();
    	//view.setSprites(fMonster);
   	
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
	}
	
	private void placePlayer(){   	
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model.set(currentRow, currentCol, '5'); //A Spartan warrior is at index 5
    	updateView(); 		
	}
	
	private void updateView(){
		view.setCurrentRow(currentRow);
		view.setCurrentCol(currentCol);
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
		if (row <= model.size() - 1 && col <= model.size() - 1 && model.get(row, col) == ' '){
			model.set(currentRow, currentCol, '\u0020');
			model.set(row, col, '5');
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	private FuzzyMonster[] getFuzzyMonsters() throws Exception {
		
		FuzzyMonster[] fMonster = new FuzzyMonster[IMAGE_COUNT];
		fMonster[0] = new FuzzyMonster("Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png");
		fMonster[1] = new FuzzyMonster("Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png");
		fMonster[2] = new FuzzyMonster("Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png");
		fMonster[3] = new FuzzyMonster("Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png");
		
		return fMonster;
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
		sprites[6] = new FuzzyMonster("Black Spider", "resources/black_spider_1.png", "resources/black_spider_2.png");
		sprites[7] = new FuzzyMonster("Blue Spider", "resources/blue_spider_1.png", "resources/blue_spider_2.png");
		sprites[8] = new FuzzyMonster("Brown Spider", "resources/brown_spider_1.png", "resources/brown_spider_2.png");
		sprites[9] = new FuzzyMonster("Green Spider", "resources/green_spider_1.png", "resources/green_spider_2.png");
		sprites[10] = new FuzzyMonster("Grey Spider", "resources/grey_spider_1.png", "resources/grey_spider_2.png");
		sprites[11] = new FuzzyMonster("Orange Spider", "resources/orange_spider_1.png", "resources/orange_spider_2.png");
		sprites[12] = new FuzzyMonster("Red Spider", "resources/red_spider_1.png", "resources/red_spider_2.png");
		sprites[13] = new FuzzyMonster("Yellow Spider", "resources/yellow_spider_1.png", "resources/yellow_spider_2.png");
		return sprites;
	}
	
	public static void main(String[] args) throws Exception{
		new GameRunner();
		
//		String fileName = "fcl/fight.fcl";
//        FIS fis = FIS.load(fileName,true);
//        
//        // Error while loading?
//        if( fis == null ) { 
//            System.err.println("Can't load file: '" + fileName + "'");
//            return;
//        }
//        FunctionBlock functionBlock = fis.getFunctionBlock("fight");
//
//        // Show 
//        JFuzzyChart.get().chart(functionBlock);
//
//        // Set inputs
//        fis.setVariable("angerLevel", 8); //Apply a value to a variable
//        fis.setVariable("weapon", 3);// Evaluate
//        // Evaluate
//        fis.evaluate();
//        
//        Variable damage = functionBlock.getVariable("damage");
//        JFuzzyChart.get().chart(damage, damage.getDefuzzifier(), true);
//        System.out.println(fis);
	}
}