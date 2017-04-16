package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.maze.*;
public class RecursiveDFSTraversator implements Traversator{
	private Node[][] maze;
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	
	public void traverse(Node[][] maze, Node node) {
		this.maze = maze;
		dfs(node);
	}
	
	private void dfs(Node node){
		if (!keepRunning) return;
		
		node.setVisited(true);	
		visitCount++;
		
		if (node.isGoalNode()){
	        time = System.currentTimeMillis() - time; //Stop the clock
	        TraversatorStats.printStats(node, time, visitCount);
	        keepRunning = false;
			return;
		}
		
		try { //Simulate processing each expanded node
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Node[] children = node.children(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				children[i].setParent(node);
				dfs(children[i]);
			}
		}
	}
}