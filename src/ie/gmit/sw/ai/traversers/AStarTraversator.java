package ie.gmit.sw.ai.traversers;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.*;

import java.util.*;
public class AStarTraversator implements Traversator{
	private Maze goal;
	private final ArrayList <Maze> path = new ArrayList<Maze>();

	public AStarTraversator(Maze goal){
		this.goal = goal;
	}

	public void traverse(Maze[][] maze, Maze node, Monster monster) {
		PriorityQueue<Maze> open = new PriorityQueue<Maze>(20, (Maze current, Maze next)-> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Maze> closed = new ArrayList<Maze>();

		open.offer(node);
		node.setPathCost(0);		
		while(!open.isEmpty()){
			node = open.poll();		
			closed.add(node);
			node.setVisited(true);	

			if (node.isGoal()){
				path.add(node);
				while (node != null){			
					node = node.getParent();
					if (node != null){ 						
						path.add(node);
					}			
				}	
				monster.setPath(path);
				unvisit(node, maze);
				break;
			}

			//Process adjacent nodes
			Maze[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				if ((children[i].getRow() <= maze.length - 1) && (children[i].getCol() <= maze[children[i].getRow()].length - 1)
						&& ((children[i].getMapItem() == ' ') || (children[i].getMapItem() == '5'))){
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
