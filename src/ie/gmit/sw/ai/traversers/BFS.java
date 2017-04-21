package ie.gmit.sw.ai.traversers;

import java.util.*;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze;

public class BFS implements Traversator{
	private LinkedList<Maze> queue = new LinkedList<Maze>();
	private ArrayList <Maze> path;
	private Maze[][] maze;
	private boolean keepRunning = true;
	private Monster monster;

	public void traverse(Maze[][] maze, Maze node, Monster monster) {
		this.maze = maze;
		this.monster = monster;
		queue.addLast(node);
		search(node);
	}

	public void search(Maze node){
		path = new ArrayList<Maze>();

		while(!queue.isEmpty()){
			if (!keepRunning) return;

			if (node.isGoal()){
				path.add(node);

				while (node != null){			
					node = node.getParent();
					if (node != null){ 						
						path.add(node);
					}			
				}								
				monster.setPath(path);

				keepRunning = false;
				return;
			}else{
				Maze[] children = node.children(maze);
				queue.removeFirst();//Remove the starting Node
				for (int i = 0; i < children.length; i++) {
					//for (int i = children.length - 1; i >= 0; i--) {			
					if ((children[i].getRow() <= maze.length - 1) && (children[i].getCol() <= maze[children[i].getRow()].length - 1)
							&& ((children[i].getMapItem() == ' ') || (children[i].getMapItem() == '5'))){
						children[i].setParent(node);
						queue.addLast(children[i]);	
					}
				}
			}

			try {
				node = queue.getFirst();
			} catch (Exception e) {
			}
		}
	}
}
