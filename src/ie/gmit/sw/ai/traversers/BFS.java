package ie.gmit.sw.ai.traversers;

import java.util.*;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze;

public class BFS implements Traversator{
	private LinkedList<Maze> queue = new LinkedList<Maze>();
	private static ArrayList <Maze> path = new ArrayList<Maze>();
	private Maze[][] maze;
	private boolean keepRunning = true;
	private int visitCount = 0;
	private long time = System.currentTimeMillis();
	private Monster monst;

	public void traverse(Maze[][] maze, Maze node, Monster monster) {
		this.maze = maze;
		this.monst = monster;
		queue.addLast(node);
		search(node);
	}

	public void search(Maze node){
		while(!queue.isEmpty()){

			if (!keepRunning) return;

			node.setVisited(true);
			visitCount++;

			try { //Simulate processing each expanded node
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


			if (node.isGoal()){
				System.out.println("Found you at " + node.toString());
				time = System.currentTimeMillis() - time; //Stop the clock
				//TraversatorStats.printStats(node, time, visitCount);

				while (node != null){			
					node = node.getParent();
					if (node != null){ 						
						path.add(node);
					}			
				}								
				monst.setPath(path);
				monst.setFound(true);

				keepRunning = false;
				return;
				//System.exit(0);
			}else{
				Maze[] children = node.children(maze);
				queue.removeFirst();
				for (int i = 0; i < children.length; i++) {
					if (children[i] != null && !children[i].isVisited()){
						if (( children[i].getRow() <= maze.length - 1) && (children[i].getCol() <= maze[children[i].getRow()].length - 1)
								&& ((maze[children[i].getRow()][children[i].getCol()].getMapItem() == ' ') || (maze[children[i].getRow()][children[i].getCol()].getMapItem() == '5'))){
							children[i].setParent(node);
							queue.addLast(children[i]);		
						}
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
