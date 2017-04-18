package ie.gmit.sw.ai.maze;

import ie.gmit.sw.ai.maze.Node.Direction;

import java.util.Random;

public class HuntAndKillMazeGenerator extends AbstractMazeGenerator {
	public HuntAndKillMazeGenerator(int rows, int cols) {
		super(rows, cols);
	}
	
	//No stack or storage enables Hunt and Kill to create very large mazes.  tends to make Mazes with a high "river" factor, but not as high as the recursive backtracker. 
	//You can make this generate Mazes with a lower river factor by choosing to enter "hunt" mode more often.
	public void generateMaze(){
		Node[][] maze = super.getMaze();
		
		Random generator = new Random();
		int randRow = generator.nextInt(maze.length);
		int randCol = generator.nextInt(maze[0].length);
		Node node = maze[randRow][randCol];
		
		//Start random walk
		while (node != null){			
			node.setVisited(true);
			Node[] adjacents = node.adjacentNodes(maze);
			super.shuffle(adjacents);
			
			boolean look = true;
			for (int i = 0; i < adjacents.length && look; i++) {
				if (!adjacents[i].isVisited()){
					Direction dir = getDirection(node, adjacents[i]);
					node.addPath(dir);
					adjacents[i].addPath(opposite(dir));
					
					node = adjacents[i];
				}
			}

			if (look) node = hunt();			
		}
	}
	
	private Node hunt(){
		Node[][] maze = super.getMaze();
		
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				if (!maze[i][j].isVisited()) return maze[i][j];
			}
		}
		return null;
	}
}
