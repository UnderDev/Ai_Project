package ie.gmit.sw.ai.maze;

import ie.gmit.sw.ai.maze.Node.*;
import java.util.*;
public class RecursiveBacktrackerMazeGenerator extends AbstractMazeGenerator {
	public RecursiveBacktrackerMazeGenerator(int rows, int cols) {
		super(rows, cols);
	}

	//Generates Mazes with about as high a "river" factor as possible, with fewer but longer dead ends, and usually a very long and meandering solution. 
	public void generateMaze(){
		Node[][] maze = super.getMaze();
		
		Random generator = new Random();
		int randRow = generator.nextInt(maze.length);
		int randCol = generator.nextInt(maze[0].length);
		Node node = maze[randRow][randCol];
		carve(node);
	}
	
	private void carve(Node node){
		node.setVisited(true);
		Node[] adjacents = node.adjacentNodes(super.getMaze());
		super.shuffle(adjacents);
		
		for (int i = 0; i < adjacents.length; i++) {
			if (!adjacents[i].isVisited()){
				Direction dir = getDirection(node, adjacents[i]);
				node.addPath(dir);
				adjacents[i].addPath(opposite(dir));
				carve(adjacents[i]);
			}
		}
	}
}
