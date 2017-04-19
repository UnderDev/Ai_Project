package ie.gmit.sw.ai.maze;

public class MazeGenerator {
	private Maze[][] maze;
	private int rows;
	private int cols;
	
	public MazeGenerator(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		buildMaze();
	}	
	public Maze[][] getMaze()
	{
		return this.maze;
	}

	private void buildMaze()
	{
		BinaryTreeMazeGenerator btMaze = new BinaryTreeMazeGenerator(rows, cols);
		this.maze = btMaze.getMaze();
	}	
}