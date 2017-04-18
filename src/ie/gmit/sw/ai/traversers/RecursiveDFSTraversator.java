package ie.gmit.sw.ai.traversers;

<<<<<<< HEAD
import ie.gmit.sw.ai.*;
public class RecursiveDFSTraversator implements Traversator{
	private Maze[][] maze;
=======
import ie.gmit.sw.ai.maze.*;
public class RecursiveDFSTraversator implements Traversator{
	private Node[][] maze;
>>>>>>> origin/FuzzyLogic
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	
<<<<<<< HEAD
	public void traverse(Maze[][] maze, Maze node) {
=======
	public void traverse(Node[][] maze, Node node) {
>>>>>>> origin/FuzzyLogic
		this.maze = maze;
		dfs(node);
	}
	
<<<<<<< HEAD
	private void dfs(Maze node){
=======
	private void dfs(Node node){
>>>>>>> origin/FuzzyLogic
		if (!keepRunning) return;
		
		node.setVisited(true);	
		visitCount++;
		
<<<<<<< HEAD
		if (node.isGoal()){
=======
		if (node.isGoalNode()){
>>>>>>> origin/FuzzyLogic
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
		
<<<<<<< HEAD
		Maze[] children = node.children(maze);
=======
		Node[] children = node.children(maze);
>>>>>>> origin/FuzzyLogic
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				children[i].setParent(node);
				dfs(children[i]);
			}
		}
	}
}