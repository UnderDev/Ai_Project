package ie.gmit.sw.ai.maze;

import java.util.Random;

import ie.gmit.sw.ai.maze.Maze.Direction;

public abstract class AbstractMazeGenerator  implements MazeGenerator{
	private Maze[][] maze;

	public AbstractMazeGenerator(int rows, int cols) {
		maze = new Maze[rows][cols];
		init();
		generateMaze();
	}
	public abstract void generateMaze();

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

	protected void shuffle(Maze[] nodes){
		Random rnd = new Random();
		int index = 0;
		Maze temp = null;
		for (int i = nodes.length - 1; i > 0; i--){
			index = rnd.nextInt(i + 1);
			temp = nodes[index];
			nodes[index] = nodes[i];
			nodes[i] = temp;
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

}