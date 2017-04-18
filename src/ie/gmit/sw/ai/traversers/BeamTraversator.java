package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.maze.*;
import java.util.*;
public class BeamTraversator implements Traversator{
	private Node goal;
	private int beamWidth= 1; 
	
	public BeamTraversator(Node goal,  int beamWidth){
		this.goal = goal;
		this.beamWidth = beamWidth;
	}
	
	public void traverse(Node[][] maze, Node node) {
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		while(!queue.isEmpty()){
			node = queue.poll();
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount);
				break;
			}
			
			try { //Simulate processing each expanded node
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Node[] children = node.children(maze);
			Collections.sort(Arrays.asList(children),(Node current, Node next) -> current.getHeuristic(goal) - next.getHeuristic(goal));
			
			int bound = 0;
			if (children.length < beamWidth){
				bound = children.length;
			}else{
				bound = beamWidth;
			}
			
			for (int i = 0; i < bound; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]);
				}
			}
		}
	}
}
