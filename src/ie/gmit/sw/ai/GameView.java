package ie.gmit.sw.ai;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ie.gmit.sw.ai.Maze.Direction;
public class GameView extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private int cellspan = 5;	
	private int cellpadding = 2;
	private Maze maze[][];
	private Sprite[] sprites;
	private int enemy_state = 5;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;
	private int offset = 48; //The number 0 is ASCII 48.
	private Color[] reds = {new Color(255,160,122), new Color(139,0,0), new Color(255, 0, 0)}; //Animate enemy "dots" to make them easier to see

	public GameView(Maze[][] maze) throws Exception{
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(300, this);
		timer.start();
	}

	public void setCurrentPosition(int row, int col){
		this.currentRow = row;
		this.currentCol = col;
	}

	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze.length - 1) - cellpadding){
			currentCol = (maze.length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		cellspan = zoomOut ? maze.length : 5;         
		final int size = DEFAULT_VIEW_SIZE/cellspan;
		g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);

		for(int row = 0; row < cellspan; row++) {
			for (int col = 0; col < cellspan; col++){  
				int x1 = col * size;
				int y1 = row * size;
				
        		int x2 = (col + 1) * size;
        		int y2 = (row + 1) * size;

				char ch = '0';

				if (zoomOut){
					ch = maze[row][col].getCharType();
					if (ch >= '5'){
						if (row == currentRow && col == currentCol){
							g2.setColor(Color.YELLOW);
						}else{
							g2.setColor(reds[(int) (Math.random() * 3)]);
						}
						g2.fillRect(x1, y1, size, size);
					}
				}else{
					ch = maze[currentRow - cellpadding + row] [currentCol - cellpadding + col].getCharType();
				}

				imageIndex = (int) ch;
				imageIndex -= offset;
				if (imageIndex < 0){
					g2.setColor(Color.LIGHT_GRAY);//Empty cell
					g2.fillRect(x1, y1, size, size);   			
				}else{
					g2.drawImage(sprites[imageIndex].getNext(), x1, y1, null);
				}
				Direction[] paths = maze[row][col].getPaths();
				
				if(paths!=null)	{			
				for (int i = 0; i < paths.length; i++){
					if (paths[i] == Direction.North){
						g2.drawLine(x1 + 1, y1, x2 - 1, y1); //N
		    		}else if (paths[i] == Direction.South){     			
		    			g2.drawLine(x1, y2, x2, y2); //S
		    		}else if (paths[i] == Direction.East){           			
		    			g2.drawLine(x2, y1, x2, y2); //E
		    		}else if (paths[i] == Direction.West){ 
		    			g2.drawLine(x1, y1 + 1, x1, y2 -1); //W
					}}
				}
			}
		}
	}

	public void toggleZoom(){
		zoomOut = !zoomOut;		
	}

	public void actionPerformed(ActionEvent e) {	
		if (enemy_state < 0 || enemy_state == 5){
			enemy_state = 6;
		}else{
			enemy_state = 5;
		}
		this.repaint();
	}

	public void setSprites(Sprite[] sprites){
		this.sprites = sprites;
	}
}