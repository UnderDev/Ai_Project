package ie.gmit.sw.ai.maze;

public class BinaryTreeMazeGenerator extends AbstractMazeGenerator {
	public BinaryTreeMazeGenerator(int rows, int cols) {
		super(rows, cols);
	}
	
	//Binary tree algorithm for creating a maze. Adds a bias into the generated structure
	//For each node in the maze (2D array), randomly create a passage either north or west, but not both
	public void generateMaze(){
		Node[][] maze = super.getMaze();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				int num = (int) (Math.random() * 10);
				if (col > 0 && (row == 0 || num >= 5)){
					maze[row][col].addPath(Node.Direction.West);
				}else{
					maze[row][col].addPath(Node.Direction.North);	
				}				
			}
		}
	}
}