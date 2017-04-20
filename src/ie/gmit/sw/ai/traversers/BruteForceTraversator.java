package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.maze.*;

import java.util.*;
public class BruteForceTraversator implements Traversator{
	private boolean dfs = false;
	
	public BruteForceTraversator(boolean depthFirst){
		this.dfs = depthFirst;
	}
	
	public void traverse(Maze[][] maze, Maze node) {
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		Deque<Maze> queue = new LinkedList<Maze>();
		queue.offer(node);
		
		while (!queue.isEmpty()){
			node = queue.poll();
			node.setVisited(true);
			visitCount++;
			
			if (node.isGoal()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
				break;
			}
			
			try { //Simulate processing each expanded node				
				Thread.sleep(10);						
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Maze[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					
					children[i].setMapItem('\u003D');//spider Char
					children[i].getParent().setMapItem('\u0020');//Space
					
					if (dfs){
						queue.addFirst(children[i]);
					}else{
						queue.addLast(children[i]);
					}
				}									
			}			
		}
	}

}