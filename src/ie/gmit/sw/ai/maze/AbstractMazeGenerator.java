package ie.gmit.sw.ai.maze;
public abstract class AbstractMazeGenerator {
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
}