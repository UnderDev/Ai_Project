package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.*;

import java.awt.Color;
import java.util.*;
public class AStarTraversator implements Traversator{
	private Maze goal;
	private final ArrayList <Maze> path = new ArrayList<Maze>();

	public AStarTraversator(Maze goal){
		this.goal = goal;
	}

	public void traverse(Maze[][] maze, Maze node, Monster monster) {
		long time = System.currentTimeMillis();
		int visitCount = 0;

		PriorityQueue<Maze> open = new PriorityQueue<Maze>(20, (Maze current, Maze next)-> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Maze> closed = new ArrayList<Maze>();

		open.offer(node);
		node.setPathCost(0);		
		while(!open.isEmpty()){
			node = open.poll();		
			closed.add(node);
			//node.setVisited(true);	
			visitCount++;

			if (node.isGoal()){
				time = System.currentTimeMillis() - time; //Stop the clock

				while (node != null){			
					node = node.getParent();
					if (node != null){ 						
						path.add(node);
					}			
				}	
				monster.setPath(path);
				break;
			}

			try { //Simulate processing each expanded node
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//Process adjacent nodes
			Maze[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				Maze child = children[i];
				int score = node.getPathCost() + 1 + child.getHeuristic(goal);
				int existing = child.getPathCost() + child.getHeuristic(goal);

				if ((open.contains(child) || closed.contains(child)) && existing < score){
					continue;
				}else{
					open.remove(child);
					closed.remove(child);
					child.setParent(node);
					child.setPathCost(node.getPathCost() + 1);
					open.add(child);
				}

			}
		}
	}
	
	
	private void unvisit(Maze node, Maze[][] maze){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze.length; j++){
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
}
}
