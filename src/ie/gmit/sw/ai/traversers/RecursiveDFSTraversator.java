package ie.gmit.sw.ai.traversers;

import java.util.LinkedList;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze;

public class RecursiveDFSTraversator implements Traversator{
	private Maze[][] maze;

	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	private LinkedList<Maze> queue = new LinkedList<Maze>();


	public void traverse(Maze[][] maze, Maze node, Monster monster) {		
		this.maze = maze;
		dfs(node);
		System.out.println("Start Node: "+node);
	}

	private void dfs(Maze node){
		if (!keepRunning) return;

		node.setVisited(true);

		visitCount++;

		if (node.isGoal()){
			System.out.println("Found you at " + node.toString());
			time = System.currentTimeMillis() - time; //Stop the clock
			TraversatorStats.printStats(node, time, visitCount);
			keepRunning = false;
			return;
		}

		try { //Simulate processing each expanded node
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Maze[] children = node.children(maze);

		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				children[i].setParent(node);
				if (( children[i].getRow() <= maze.length - 1) && (children[i].getCol() <= maze[children[i].getRow()].length - 1)
						&& (maze[children[i].getRow()][children[i].getCol()].getMapItem() == ' ')){
					children[i].setMapItem('\u003D');//spider Char
					queue.add(children[i]);
				}

				dfs(children[i]);
			}
		}

	}
}