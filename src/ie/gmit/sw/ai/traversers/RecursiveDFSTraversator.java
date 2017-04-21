package ie.gmit.sw.ai.traversers;

import java.util.ArrayList;
import java.util.LinkedList;

import ie.gmit.sw.ai.Monster;
import ie.gmit.sw.ai.maze.Maze;

public class RecursiveDFSTraversator implements Traversator{
	private Maze[][] maze;

	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	private final  LinkedList<Maze> queue = new LinkedList<Maze>();
	private final  ArrayList <Maze> path = new ArrayList<Maze>();
	private Monster monster;
	private Maze tempNode;

	public void traverse(Maze[][] maze, Maze node, Monster monster) {		
		this.maze = maze;
		this.monster = monster;
		this.tempNode = node;
		dfs(node);
	}

	private void dfs(Maze node){
		if (!keepRunning) return;

		//node.setVisited(true);

		visitCount++;


		try { //Simulate processing each expanded node
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (node.isGoal()){
			System.out.println(Thread.currentThread().getName()+" Found you at " + node.toString());
			//time = System.currentTimeMillis() - time; //Stop the clock
			//TraversatorStats.printStats(node, time, visitCount);
			node.setGoal(true);

			System.out.println(Thread.currentThread().getName());
			while (node != null){			
				node = node.getParent();
				if (node != null){ 						
					path.add(node);
				}			
			}								
			monster.setPath(path);
			monster.setFound(true);

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
						
						if(Thread.currentThread().getName() =="Spider 1")
							children[i].setMapItem('\u0036');//spider Char
						else 
							children[i].setMapItem('\u0037');//spider Char
						
						children[i].setParent(node);
						dfs(children[i]);}
				}
			}
		}

		/*try { //Simulate processing each expanded node
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Maze[] children = node.children(maze);

		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				children[i].setParent(node);
				if ((children[i].getRow() <= maze.length - 1) && (children[i].getCol() <= maze[children[i].getRow()].length - 1)
						&& ((children[i].getMapItem() == ' ') || (children[i].getMapItem() == '5'))){
					//children[i].setMapItem('\u003D');//spider Char
					queue.add(children[i]);
				}

				dfs(children[i]);
			}
		}
		 */



	}
}