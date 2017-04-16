package ie.gmit.sw.ai.maze;

import ie.gmit.sw.ai.maze.Node.Direction;

import java.util.*;
public class RandomDepthFirstMazeGenerator extends AbstractMazeGenerator {
	public RandomDepthFirstMazeGenerator(int rows, int cols) {
		super(rows, cols);
	}
	
	//Generates Mazes with a low branching factor and high a "river" factor because the algorithm explores as far as possible along each branch before backtracking.
	public void generateMaze(){
		Node[][] maze = super.getMaze();
		
		Random generator = new Random();
		int randRow = generator.nextInt(maze.length);
		int randCol = generator.nextInt(maze[0].length);
		Node node = maze[randRow][randCol];
		
		Deque<Node> stack = new LinkedList<Node>();
		stack.addFirst(node);
		
		while (!stack.isEmpty()){
			node = stack.poll();
			node.setVisited(true);
			
			Node[] adjacents = node.adjacentNodes(maze);
			super.shuffle(adjacents);
			
			for (int i = 0; i < adjacents.length; i++) {
				if (!adjacents[i].isVisited()){
					stack.addFirst(adjacents[i]);
					Direction dir = getDirection(node, adjacents[i]);
					node.addPath(dir);
					adjacents[i].addPath(opposite(dir));
					adjacents[i].setVisited(true);
				}
			}
		}
	}
}