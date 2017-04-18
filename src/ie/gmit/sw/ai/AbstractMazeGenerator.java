package ie.gmit.sw.ai;

import ie.gmit.sw.ai.Maze.Direction;

import java.awt.Color;
import java.util.*;
public abstract class AbstractMazeGenerator {
	private Maze[][] maze;
	private Maze goal;

	public AbstractMazeGenerator(int rows, int cols) {
		maze = new Maze[rows][cols];
		init();
		generateMaze();
		setGoalNode();
		unvisit();
	}
	
	public abstract void generateMaze();

	public void setGoalNode() {//ADD SPARTEN HERE
		Random generator = new Random();
		int randRow = generator.nextInt(maze.length);
		int randCol = generator.nextInt(maze[0].length);
		maze[randRow][randCol].setGoal(true);
		goal = maze[randRow][randCol];
	}

	public Maze getGoalNode() {
		return goal;
	}

	public Maze[][] getMaze() {
		return maze;
	}
	
	protected void init() {
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Maze(row,col);
			}
		}
	}
	
	protected void unvisit(){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
				maze[i][j].setColor(Color.BLACK);
			}
		}
	}

	protected void shuffle(Maze[] adjacents){
		Random rnd = new Random();
		int index = 0;
		Maze temp = null;
	    for (int i = adjacents.length - 1; i > 0; i--){
	        index = rnd.nextInt(i + 1);
	        temp = adjacents[index];
	        adjacents[index] = adjacents[i];
	        adjacents[i] = temp;
	    }
	}
	
	protected Direction getDirection(Maze current, Maze adjacent){
		if (adjacent.getRow() == current.getRow() - 1 && adjacent.getCol() == current.getCol()) return Direction.North;
		if (adjacent.getRow() == current.getRow() + 1 && adjacent.getCol() == current.getCol()) return Direction.South;
		if (adjacent.getRow() == current.getRow() && adjacent.getCol() == current.getCol() - 1) return Direction.West;
		if (adjacent.getRow() == current.getRow() && adjacent.getCol() == current.getCol() + 1) return Direction.East;
		return null;
	}
	
	protected Direction opposite(Direction dir){
		if (dir == Direction.North) return Direction.South;
		if (dir == Direction.South) return Direction.North;
		if (dir == Direction.East) return Direction.West;
		if (dir == Direction.West) return Direction.East;
		return null;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				Maze.Direction[] dirs = maze[row][col].getPaths();
				sb.append("(");
				for (int i = 0; i < dirs.length; i++){
					sb.append(dirs[i]);
				}
				sb.append(") ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}