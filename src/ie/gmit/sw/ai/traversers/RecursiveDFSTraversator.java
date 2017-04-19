package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.maze.Maze;

public class RecursiveDFSTraversator implements Traversator{
	private Maze[][] maze;

	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;


	public void traverse(Maze[][] maze, Maze node) {

		this.maze = maze;
		dfs(node);
	}


	private void dfs(Maze node){

		if (!keepRunning) return;

		node.setVisited(true);


		if (node.getRow() <= maze.length - 1 && node.getCol()  <= maze[node.getRow()].length - 1 && maze[node.getRow()][node.getCol() ].getMapItem() == ' '){
			maze[node.getRow()][node.getCol()].setMapItem('\u0020');//Space

			maze[node.getRow()][node.getCol()].setMapItem('\u003D');//Hero Char	
		}



		visitCount++;

		if (node.isGoal()){
			System.out.println("Found you at " + node.toString());
			time = System.currentTimeMillis() - time; //Stop the clock
			TraversatorStats.printStats(node, time, visitCount);
			keepRunning = false;
			return;
		}

		try { //Simulate processing each expanded node
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Maze[] children = node.children(maze);

		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){

				children[i].setParent(node);
				dfs(children[i]);
			}
		}
	}
}