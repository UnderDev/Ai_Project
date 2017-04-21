package ie.gmit.sw.ai.traversers;

import java.util.ArrayList;
import java.util.Collections;
import ie.gmit.sw.ai.maze.Maze;

//Not used, but prints out the stats of the node passed in, eg goal node
public class TraversatorStats {
	private ArrayList <Maze> path = new ArrayList<Maze>();
	public void printStats(Maze node, long time, int visitCount){

		double depth = 0;

		while (node != null){			
			node = node.getParent();
			if (node != null){ 
				node.setMapItem('\u0036');
				path.add(node);
			}
			depth++;			
		}
		Collections.reverse(path);
		System.out.println("Visited " + visitCount + " nodes in " + time + "ms.");
		System.out.println("Found goal at a depth of " + String.format("%.0f", depth));    
		System.out.println("EBF = B* = k^(1/d) = " + String.format("%.2f", Math.pow((double) visitCount, (1.00d / depth)))); 
		System.out.println("Path To Goal Node: "+ path);
	}
}