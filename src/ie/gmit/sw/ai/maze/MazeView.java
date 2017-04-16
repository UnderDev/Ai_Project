package ie.gmit.sw.ai.maze;

import ie.gmit.sw.ai.maze.Node.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class MazeView extends JPanel implements ActionListener{
	private Timer timer=new Timer(50, this);
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_VIEW_SIZE = 800;	
	private Node[][] maze;
	private int currentRow;
	private int currentCol;
	
	public MazeView(Node[][] maze, Node goal) {
		this.maze = maze;
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		timer.start();
	}
	
	public void setCurrentPosition(int row, int col){
		this.currentRow = row;
		this.currentCol = col;
	}
	
	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

	public int getCurrentCol() {
		return currentCol;
	}

	public void setCurrentCol(int currentCol) {
		this.currentCol = currentCol;
	}

	public void actionPerformed(ActionEvent ev){
		if(ev.getSource()==timer){
			 repaint();
		}
	}
		
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        final int size = DEFAULT_VIEW_SIZE/maze.length;
        g2.drawRect(0, 0, MazeView.DEFAULT_VIEW_SIZE, MazeView.DEFAULT_VIEW_SIZE);
     
        for(int row = 0; row < maze.length; row++) {
        	for (int col = 0; col < maze[row].length; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		int x2 = (col + 1) * size;
        		int y2 = (row + 1) * size;
        		
        		
        		if (maze[row][col].isVisited() && !maze[row][col].isGoalNode()){
        			g2.setColor(maze[row][col].getColor());
        			g2.fillRect(x1, y1, size, size);
        		}
        		
       			if (maze[row][col].isGoalNode()){
       				g2.setColor(Color.GREEN);
       				g2.fillRect(x1, y1, size, size);
       			}
       			
        		g2.setColor(Color.RED);
        		g2.drawLine(x1, y1, x2, y1); //N
        		g2.drawLine(x1, y2, x2, y2); //S
        		g2.drawLine(x2, y1, x2, y2); //E
        		g2.drawLine(x1, y1, x1, y2); //W
        		
        		g2.setColor(maze[row][col].getColor());
        		
        		Direction[] paths = maze[row][col].getPaths();
        		for (int i = 0; i < paths.length; i++){
        			if (paths[i] == Direction.North){
        				g2.drawLine(x1 + 1, y1, x2 - 1, y1); //N
            		}else if (paths[i] == Direction.South){     			
            			g2.drawLine(x1, y2, x2, y2); //S
            		}else if (paths[i] == Direction.East){           			
            			g2.drawLine(x2, y1, x2, y2); //E
            		}else if (paths[i] == Direction.West){ 
            			g2.drawLine(x1, y1 + 1, x1, y2 -1); //W
        			}
        		}
        	}
        }
	}
}