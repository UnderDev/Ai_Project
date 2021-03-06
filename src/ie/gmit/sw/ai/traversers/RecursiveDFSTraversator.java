package ie.gmit.sw.ai.traversers;

import java.util.ArrayList;
import java.util.LinkedList;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze;
/*
 * A depth-first search (DFS) is a commonly used algorithm for traversing or searching a
 * tree or semantic network (graph). DFS is an example of a �generate and test�
 * approach to searching a state space. DFS also illustrates the use of brute-force or
 * exhaustive search algorithms. Because it is memory efficient, DFS has been applied,
 * in various forms, to a wide variety of search problems. These include file system
 * searching and web spiders and indexers
 */
public class RecursiveDFSTraversator implements Traversator{
	private Maze[][] maze;

	private boolean keepRunning = true;
	private final  ArrayList <Maze> path = new ArrayList<Maze>();
	private Monster monster;

	public void traverse(Maze[][] maze, Maze node, Monster monster) {		
		this.maze = maze;
		this.monster = monster;
		dfs(node);
	}

	private void dfs(Maze node){
		if (!keepRunning) return;

		path.add(node);
		if (node.isGoal()){
			node.setGoal(true);
			while (node != null){			
				node = node.getParent();
				if (node != null){ 						
					path.add(node);
				}			
			}								
			monster.setPath(path);

			keepRunning = false;
			return;
		}
		else{
			if (!node.isVisited()){
				node.setVisited(true);
				Maze[] children = node.children(maze);
				for (int i = 0; i < children.length; i++) {
					if ((children[i].getRow() <= maze.length - 1) && (children[i].getCol() <= maze[children[i].getRow()].length - 1)
							&& ((children[i].getMapItem() == ' ') || (children[i].getMapItem() == '5'))){
						children[i].setParent(node);
						dfs(children[i]);}
				}
			}
		}
	}
}